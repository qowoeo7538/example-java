package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

class UnsafeThreadDemo {

    private static final Unsafe THE_UNSAFE = UnsafeUtils.getUnsafe();

    @Test
    void demoParkInterrupt() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-start");
            // isAbsolute: true 绝对时间为毫秒
            THE_UNSAFE.park(true, System.currentTimeMillis() + 2000);
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + "-interrupte");
            }
            System.out.println(Thread.currentThread().getName() + "-end");
        });

        t.start();

        System.out.println(Thread.currentThread().getName() + "-run");

        t.interrupt();
        t.join();
    }

    /**
     * park: 阻塞当前线程直到一个unpark方法出现(被调用)、一个用于unpark方法已经出现过(在此park方法调用之前已经调用过)、线程被中断或者time时间到期(也就是阻塞超时)。
     * 在time非零的情况下，如果isAbsolute为true，time是相对于新纪元之后的毫秒，否则time表示纳秒。这个方法执行时也可能不合理地返回(没有具体原因)。
     * 并发包java.util.concurrent中的框架对线程的挂起操作被封装在LockSupport类中，LockSupport类中有各种版本pack方法，但最终都调用了Unsafe#park()方法。
     * <p>
     * unpark: 释放被park创建的在一个线程上的阻塞。这个方法也可以被使用来终止一个先前调用park导致的阻塞。这个操作是不安全的，
     * 因此必须保证线程是存活的(thread has not been destroyed)。从Java代码中判断一个线程是否存活的是显而易见的，但是从native代码中这机会是不可能自动完成的。
     */
    @Test
    void demoParkAndUnpark() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-start");
            // isAbsolute: false 非绝对时间为纳秒
            THE_UNSAFE.park(false, 2000 * 1000000);
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + "-interrupte");
            }
            System.out.println(Thread.currentThread().getName() + "-end");
        });

        t.start();

        System.out.println(Thread.currentThread().getName() + "-run");

        THE_UNSAFE.unpark(t);
        t.join();
    }

    /**
     * 锁定对象，必须通过monitorExit方法才能解锁。此方法经过实验是可以重入的，也就是可以多次调用，然后通过多次调用monitorExit进行解锁。
     */
    @Test
    void demoMonitorEnter() {

    }

    /**
     * 尝试锁定对象，如果加锁成功返回true，否则返回false。必须通过monitorExit方法才能解锁。
     */
    void demoTryMonitorEnter() {

    }

    /**
     * 解锁对象，前提是对象必须已经调用monitorEnter进行加锁，否则抛出IllegalMonitorStateException异常。
     */
    @Test
    void demoMonitorExit() {

    }

    /**
     * 获取系统的平均负载值，loadavg这个double数组将会存放负载值的结果，nelems决定样本数量，nelems只能取值为1到3，分别代表最近1、5、15分钟内系统的平均负载。
     * 如果无法获取系统的负载，此方法返回-1，否则返回获取到的样本数量(loadavg中有效的元素个数)。实验中这个方法一直返回-1，其实完全可以使用JMX中的相关方法替代此方法。
     */
    @Test
    void demoGetLoadAverage(){

    }

}
