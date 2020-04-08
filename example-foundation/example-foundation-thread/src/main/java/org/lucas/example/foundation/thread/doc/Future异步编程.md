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
任务状态:
任务状态初始化为 NEW；当通过 set、setException、cancel 函数设置任务结果时，任务会转换为 终止状态；在任务完成过程中，任务状态可能会变为 COMPLETING（当结果被使用 set 方法设置 时）， 也可能会经过 INTERRUPTING 状态（ 当使用 cancel( true) 方法取消任务并中断任务 时）。当任务被中断后，任务状态为 INTERRUPTED；当任务被取消后，任务状态为 CANCELLED；当任务 正常终止时，任务状态为 NORMAL；当任务执行异常后，任务状态会变为 EXCEPTIONAL。

- NEW → COMPLETING → NORMAL： 正常终流程转换。
- NEW → COMPLETING → EXCEPTIONAL： 执行过程中发生异常流程转换。
- NEW → CANCELLED： 任务还没开始就被取消。
- ·NEW → INTERRUPTING → INTERRUPTED： 任务被中断。

## 3 CompletableFuture
底层使用ForkJoin,将任务分为多个子数据集，而每个子集，都可以独立处理，最后将每个子任务的结果汇集起来。

特点：
- 可以合并两个相互独立的异步计算的结果。
- 可以等待异步任务的所有任务都完成或者其中一个任务完成就返回结果。
- 任务完成后调用回调方法.
- 任务完成的结果可以用于下一个任务。
- 任务完成时发出通知.
- 提供原生的异常处理api.
