# 异步工具

[TOC]

## 1 Future 接口
当计算结果完成时只能通过提供的 get 系列方法来获取结果，如果使用了不带超时时间的 get 方法，则在计算结果完成前，调用线程会被一直阻塞。另外计算任务是可以使用 cancel 方法来取消 的，但是一旦一个任务计算完成，则不能再被取消 了。

接口方法:
- V get() : 等待异步计算任务完成，并返回结果；如果当前任 计算还没完成则会阻塞调用线程直到任务完成；如果在等待结果的过程中有其他线程取消了该任务，则调用线程抛出CancellationException 异常；如果在等待结果的过程中有其他线程中断了该线程，则调用线程抛出 InterruptedException 异常；如果任务计算过程中抛出了异常，则调用线程会抛出ExecutionException 异常。
- V get(long , TimeUnit) : 相比 get() 方法多了超时时间，当线程调用了该方法后，在任务结果没有 计算出来前调用线程不会一直被阻塞，而是会在等待 timeout 个 unit 单位的时间后抛出 TimeoutException 异常后返回。添加超时时间避免了调用线程死等的情况，让调用线程可以及时 释放。
- boolean isDone() : 如果计算任务已经完成则返回 true，否则返回 false。需要注意的是，任务完成是指任务正常完成了、由于抛出异常而完成了或者任务被取消了。
- boolean cancel(boolean) : 尝试取消任务的执行；如果当前任务已经完成或者任务已经被取消了，则尝试取消任务会失败；如果任务还没被执行时调用了用了取消任务，则任务将永远不会被执行；如果任务已经开始运行了，这时候取消任务，则参数 mayInterruptIfRunning 将决定是否要将 正在执行任务的线程中断，如果 为 true 则标识要中断， 否则标识不中断；当调用取消任务后，再 调用 isDone() 方法，后者会返回 true，随后调用 isCancelled() 方法也会一直返回 true；如果任务不能被取消，比如任务完成后已经被取消了，则该方法会返回 false。
- boolean isCancelled() : 如果任务在执行完毕前被取消了，则该方法返回true，否则返回 false。

## 2 FutureTask

### 2.1 参数含义:

- Callable<V> callable : 返回值的可执行任务.
- Object outcome : 任务返回结果. 可以通过 get() 方法来获取该结果。另外，outcome 这里没有没有被修饰为 volatile，是因为变量 state 已经被 volatile 修饰了，这里是借用 volatile 的内存语义来保证写入 outcome 时会把值刷新到主内存，读取时会从主内存读取，从而避免多线程下内存不可见问题.
- Thread runner : 运行该任务的线程，这个是在 FutureTask 的 run 方法内使用 CAS 函数设置的。
- WaitNode waiters : 用 Treiber stack 实现的无锁栈，栈顶元素用 waiters 代表。栈用来记录所有等待任务结果的线程节点.
- long stateOffset : state 变量的偏移地址, 实现 CAS 操作.
- long runnerOffset : runner 变量的偏移地址, 实现 CAS 操作.
- long waitersOffset : waiters 变量的偏移地址, 实现 CAS 操作.

### 2.2 任务状态:

任务状态初始化为 NEW；当通过 set、setException、cancel 函数设置任务结果时，任务会转换为 终止状态；在任务完成过程中，任务状态可能会变为 COMPLETING（当结果被使用 set 方法设置 时）， 也可能会经过 INTERRUPTING 状态（ 当使用 cancel( true) 方法取消任务并中断任务 时）。当任务被中断后，任务状态为 INTERRUPTED；当任务被取消后，任务状态为 CANCELLED；当任务 正常终止时，任务状态为 NORMAL；当任务执行异常后，任务状态会变为 EXCEPTIONAL。

- NEW → COMPLETING → NORMAL： 正常终流程转换。
- NEW → COMPLETING → EXCEPTIONAL： 执行过程中发生异常流程转换。
- NEW → CANCELLED： 任务还没开始就被取消。
- NEW → INTERRUPTING → INTERRUPTED： 任务被中断。

### 2.3 源码解析

#### 2.3.1 构造方法

```java
    public FutureTask(Callable<V> callable) {
        if (callable == null)
            throw new NullPointerException();
        this.callable = callable;
        // 写入 state 的值可以保证 callable 的写入也会被刷入主内存
        // 以避免多线程下内存不可见问题。
        this.state = NEW;
    }
```

#### 2.3.2 FutureTask#run()

该方法是任务的执行体，线程是调用该方法来具体运行任务的，如果任务没有被取消，则该方法会运行任务，并且将结果设置到 outcome 变量中.

```java
    public void run() {
        // 1. 如果任务不是初始化的NEW状态，或者使用CAS设置runner为当前线程失败，则直接返回。
        // 防止同一个 FutureTask 对象被提交给多个线程来执行，导致 run() 方法被多个线程同时执行造成混乱。
        if (state != NEW ||
                !UNSAFE.compareAndSwapObject(this, runnerOffset,
                        null, Thread.currentThread())) {
            return;
        }
        // 2. 如果任务不为 null，并且任务状态为 NEW ，则执行任务
        try {
            Callable<V> c = callable;
            if (c != null && state == NEW) {
                V result;
                boolean ran;
                try {
                    // 2.1 执行任务，如果 OK 则设置 ran 标记为 true
                    result = c.call();
                    ran = true;
                } catch (Throwable ex) {
                    // 2.2 执行任务出现异常，则设置 ran 为 false, result 为 null,
                    // 并且设置异常, 任务处于异常状态.
                    result = null;
                    ran = false;
                    setException(ex);
                }
                // 3. 任务执行正常，则设置结果
                if (ran) {
                    set(result);
                }
            }
        } finally {
            // 在设置状态之前，运行程序必须是非空的，以防止对 run() 的并发调用
            runner = null;
            int s = state;
            // 4. 为了保证调用 cancel(true) 的线程在该 run() 方法返回前中断任务执行的线程
            if (s >= INTERRUPTING)
                handlePossibleCancellationInterrupt(s);
        }
    }

    private void handlePossibleCancellationInterrupt(int s) {
        // 为了保证调用 cancel() 在该 run() 方法返回前中断任务执行的线程
        // 这里使用 Thread.yield() 让 run() 方法执行线程让出CPU执行权，以便让
        // cancel(true) 的线程执行 cancel(true) 中的代码中断任务线程
        if (s == INTERRUPTING) {
            while (state == INTERRUPTING) {
                // wait out pending interrupt
                Thread.yield();
            }
        }


        // assert state == INTERRUPTED;

        // We want to clear any interrupt we may have received from
        // cancel(true).  However, it is permissible to use interrupts
        // as an independent mechanism for a task to communicate with
        // its caller, and there is no way to clear only the
        // cancellation interrupt.
        //
        // Thread.interrupted();
    }

    protected void setException(Throwable t) {
        // 使用 CAS 尝试设置任务状态 state 为 COMPLETING
        // 如果 CAS 成功，则把异常信息设置到 outcome 变量
        // 并且设置任务状态为 EXCEPTIONAL 终止状态，然后调用finishCompletion
        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, COMPLETING)) {
            outcome = t;
            UNSAFE.putOrderedInt(this, stateOffset, EXCEPTIONAL);
            finishCompletion();
        }
    }

    private void finishCompletion() {
        // a 遍历链表节点
        for (FutureTask.WaitNode q; (q = waiters) != null; ) {
            // a.1 CAS 设置当前节点为 null.
            // 激活 waiters 链表中所有由于等待获取结果而被阻塞的线程，并从waiters链表中移除它们.
            if (UNSAFE.compareAndSwapObject(this, waitersOffset, q, null)) {
                for (; ; ) {
                    // 唤醒当前 q 节点对应的线程。
                    Thread t = q.thread;
                    if (t != null) {
                        q.thread = null;
                        LockSupport.unpark(t);
                    }
                    // 获取 q 的下一个节点
                    FutureTask.WaitNode next = q.next;
                    if (next == null)
                        break;
                    q.next = null; // unlink to help gc
                    q = next;
                }
                break;
            }
        }
        // b 所有阻塞的线程被唤醒后调用 done().
        done();

        callable = null;        // to reduce footprint
    }

    protected void set(V v) {
        // 使用 CAS 尝试设置任务状态 state 为 COMPLETING，如
        // 果 CAS 成功，则把任务结果设置到 outcome 变量，并
        // 且将任务状态设置为 NORMAL 终止状态，然后
        // 调用 finishCompletion 唤醒所有因为等待结果而被阻塞的线程。
        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, COMPLETING)) {
            outcome = v;
            UNSAFE.putOrderedInt(this, stateOffset, NORMAL); // final state
            finishCompletion();
        }
    }

```

#### 2.3.3 FutureTask#get()

等待异步计算任务完成，并返回结果；如果当前任务计算还没完成则会阻塞调用线程直到任务完成；如果在等待结果的过程中有其他线程取消了该任务，则调用线程会抛出 CancellationException 异常；如果在等待结果的过程中有线程中断了该线程，则抛出 InterruptedException 异常；如果任务计算过程中抛出了异常，则会抛出 ExecutionException异常。

创建一个 FutureTask 时，其任务状态初始化为 NEW，当我们把任务提交到线程或者线程池后，会有一个线程来执行该 FutureTask 任务，具体是调用其 run() 方法来执行任务。在任务执行过程中，我们可以在其他线程调用 FutureTask 的 get() 方法来等待获取结果，如果当前任务还在执行，则调用get的线程会被阻塞然后放入 FutureTask 内的阻塞链表队列；多个线程可以同时调用 get() 方法，这些线程可能都会被阻塞并放到阻塞链表队列中。当任务执行完毕后会把结果或者异常信息设置到 outcome 变量，然后会移除和唤醒 FutureTask 内阻塞链表队列中的线程节点，进而这些由于调用 FutureTask 的 get() 方法而被阻塞的线程就会被激活。

```java
    public V get() throws InterruptedException, ExecutionException {
        // 1 获取状态，如有需要则等待
        int s = state;
        if (s <= COMPLETING)
            // 如果任务状态的值小于等于 COMPLETING，则说明任务还没有完成，所以调用 waitDone() 挂起调用线程。
            s = awaitDone(false, 0L);
        // 返回结果
        return report(s);
    }

    private int awaitDone(boolean timed, long nanos)
            throws InterruptedException {
        // 1.1 获取设置的超时时间，如果传递的 timed 为 false 说明没有设置超时时间，则
        // deadline 设置为0
        final long deadline = timed ? System.nanoTime() + nanos : 0L;
        FutureTask.WaitNode q = null;
        boolean queued = false;
        // 1.2 循环等待任务完成
        for (; ; ) {
            // 1.2.1 如果任务被中断，则从等待链表中移除当前线程对应的节点（如果队列里面有该节点的话），
            // 然后抛出InterruptedException异常；
            if (Thread.interrupted()) {
                removeWaiter(q);
                throw new InterruptedException();
            }
            // 1.2.2 如果当前任务状态大于 COMPLETING，说明任务已经进入了终态（可能是NORMAL、EXCEPTIONAL、CANCELLED、INTERRUPTED中的一种），
            // 则把执行任务的线程的引用设置为null，并且返回结果。
            int s = state;
            if (s > COMPLETING) {
                if (q != null)
                    q.thread = null;
                return s;
            }
            // 1.2.3 如果当前任务状态为 COMPLETING，说明任务已经接近完成了，只有结果还未设置到 outcome 中，
            // 则这时让当前线程放弃 CPU 执行， 意在让任务执行线程获取到 CPU 从而将任务状态从COMPLETING转换到终态 NORMAL，
            // 这样可以避免当前调用 get() 方法的线程被挂起，然后再被唤醒的开销。
            else if (s == COMPLETING) {
                // cannot time out yet
                Thread.yield();
            }
            // 1.2.4 如果当前 q 为 null，则创建一个与当前线程相关的节点.
            else if (q == null) {
                q = new FutureTask.WaitNode();
            }
            // 1.2.5 如果当前线程对应节点还没放入 waiters 管理的等待列表，则使用 CAS 操作放入。
            else if (!queued) {
                queued = UNSAFE.compareAndSwapObject(this, waitersOffset,
                        q.next = waiters, q);
            }
            // 1.2.6 如果设置了超时时间则使用 LockSupport.parkNanos(this，nanos) 让当前线程挂起 deadline 时间，
            // 否则会调用“LockSupport.park(this)；”让线程一直挂起直到其他线程调用了unpark方法，
            // 并且以当前线程为参数（比如finishCompletion()方法）。
            else if (timed) {
                nanos = deadline - System.nanoTime();
                if (nanos <= 0L) {
                    removeWaiter(q);
                    return state;
                }
                LockSupport.parkNanos(this, nanos);
            }
            // 1.2.7 没有设置超时时间
            else {
                LockSupport.park(this);
            }
        }
    }
```

#### 2.3.4 FutureTask#cancel(boolean mayInterruptIfRunning)

尝试取消任务的执行，如果当前任务已经完成或者任务已经被取消了，则尝试取消任务会失败；如果任务还没被执行时调用了取消任务，则任务将永远不会被执行；如果任务已经开始运行了，这时取消任务，则由参数mayInterruptIfRunning 决定是否要将正在执行任务的线程中断，如果为 true 则标识要中断，否则标识不中断。当调用取消任务后，再调用 isDone() 方法，后者会返回 true，随后调用 isCancelled() 方法也会一直返回 true；如果任务不能被取消，比如任务已经完成了，任务已经被取消了，则该方法会返回false。

```java
    public boolean cancel(boolean mayInterruptIfRunning) {
        // 1 如果任务状态为 New 则使用 CAS 设置任务状态为 INTERRUPTING 或者 ANCELLED，如果 mayInterruptIfRunning 设置为 true，
        // 说明要中断正在执行任务的线程，则使用CAS设置任务状态为 INTERRUPTING，否则设置为 CANCELLED；如果 CAS 失败则直接返回 false。
        if (!(state == NEW &&
                UNSAFE.compareAndSwapInt(this, stateOffset, NEW,
                        mayInterruptIfRunning ? INTERRUPTING : CANCELLED))) {
            return false;
        }
        try {
            // 2 如果设置了中断正常执行任务线程，则中断
            if (mayInterruptIfRunning) {
                try {
                    Thread t = runner;
                    if (t != null)
                        t.interrupt();
                } finally { // final state
                    UNSAFE.putOrderedInt(this, stateOffset, INTERRUPTED);
                }
            }
        } finally {
            // 3 移除并激活所有因为等待结果而被阻塞的线程
            finishCompletion();
        }
        return true;
    }
```




## 3 CompletableFuture

1. CompletableFuture 可以显式地设置计算结果和状态以便让任务结束的 Future，并且其可以作为一个 CompletionStage（计算阶段），当它的计算完成时可以触发一个函数或者行为；当多个线程企图调用同一个 CompletableFuture 的 complete、cancel 方式时只有一个线程会成功。CompletableFuture 除了含有可以直接操作任务状态和结果的方法外，还实现了 CompletionStage 接口的一些方法，这些方法遵循：

- 当 CompletableFuture 任务完成后，同步使用任务执行线程来执行依赖任务结果的函数或者行为
- 所有异步的方法在没有显式指定 Executor 参数的情形下都是复用 ForkJoinPool.commonPool() 线程池来执行。
- 所有 CompletionStage 方法的实现都是相互独立的，以便一个方法的行为不会因为重载了其他方法而受影响。

2. 一个 CompletableFuture 任务可能有一些依赖其计算结果的行为方法，这些行为方法被收集到一个无锁基于 CAS 操作来链接起来的链表组成的栈中；当 CompletableFuture 的计算任务完成后，会自动弹出栈中的行为方法并执行。由于是栈结构，在同一个 CompletableFuture 对象上行为注册的顺序与行为执行的顺序是相反的。

3. 底层默认情况使用的是整个 JVM 唯一的 ForkJoinPool.commonPool, 将任务分为多个子数据集，而每个子集，都可以独立处理，最后将每个子任务的结果汇集起来。

4. 特点：

- 可以合并两个相互独立的异步计算的结果。
- 可以等待异步任务的所有任务都完成或者其中一个任务完成就返回结果。
- 任务完成后调用回调方法.
- 任务完成的结果可以用于下一个任务。
- 任务完成时发出通知.
- 提供原生的异常处理api.

### 3.1 原理

#### 3.1.1 参数
- result：存放任务执行的结果，如果不为 null，则标识任务已经执行完成。而计算任务本身也可能需要返回null值，所以使用 AltResult 来包装计算任务返回 null 的情况(ex等于 null 的时候)，AltResult也被用来存放当任务执行出现异常时候的异常信息（ex不为 null 的时候）
- stack：

#### 3.1.2 CompletionStage

1. CompletableFuture 实现了 CompletionStage 接口，一个 CompletionStage 代表着一个异步计算节点，当另外一个 CompletionStage 计算节点完成后，当前 CompletionStage 会执行或者计算一个值；一个节点在计算终止时完成，可能反过来触发其他依赖其结果的节点开始计算。
2. 一个节点（CompletionStage）的计算执行可以被表述为一个函数、消费者、可执行的Runable（例如使用apply、accept、run方法），具体取决于这个节点是否需要参数或者产生结果。

```java
        stage.thenApply(x -> square(x))// 计算平方和 
                .thenAccept(x -> System.out.print(x))// 输出计算结果 
                .thenRun(() -> System.out.println());// 然后执行异步任务
```

3. CompletionStage 节点可以使用3种模式来执行：默认执行、默认异步执行（使用async后缀的方法）和用户自定义的线程执行器执行（通过传递一个Executor方式）。
4. 一个节点的执行可以通过一到两个节点的执行完成来触发。一个节点依赖的其他节点通常使用then前缀的方法来进行组织。

