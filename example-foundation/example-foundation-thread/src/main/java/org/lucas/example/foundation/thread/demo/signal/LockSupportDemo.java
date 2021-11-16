package org.lucas.example.foundation.thread.demo.signal;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * Object getBlocker(Thread t)：                  返回提供给最近一次尚未解除阻塞的 park 方法调用的 blocker 对象，如果该调用不受阻塞，则返回 null。
 * void park()：                                  为了线程调度，禁用当前线程，除非许可可用。
 * void park(Object blocker)：                    为了线程调度，在许可可用之前禁用当前线程。
 * void parkNanos(long nanos)：                   为了线程调度禁用当前线程，最多等待指定的等待时间，除非许可可用。
 * void parkNanos(Object blocker, long nanos)：   为了线程调度，在许可可用前禁用当前线程，并最多等待指定的等待时间。
 * void parkUntil(long deadline)：                为了线程调度，在指定的时限前禁用当前线程，除非许可可用。
 * void parkUntil(Object blocker, long deadline)：为了线程调度，在指定的时限前禁用当前线程，除非许可可用。
 * void unpark(Thread thread)：                   如果给定线程的许可尚不可用，则使其可用。
 */
class LockSupportDemo {

    @Test
    void demoLockSupport() {
        // 1次 unpark 给线程许可, 如果某个线程没有调用park方法，而是先调用了unpark方法，则再调用park方法时不会阻塞。
        LockSupport.unpark(Thread.currentThread());
        // 如果线程非阻塞重复调用没有任何效果
        LockSupport.unpark(Thread.currentThread());
        // 消耗一个许可
        LockSupport.park(Thread.currentThread());
        // 阻塞
        LockSupport.park(Thread.currentThread());
    }

    /**
     * park()与unpark()需要成对儿使用
     */
    @Test
    void demoParkAndUnpark() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start run");
            LockSupport.park(this);
            System.out.println(Thread.currentThread().getName() + " stop run");
        });

        t.start();
        LockSupport.unpark(t);
        t.join();
        System.out.println(Thread.currentThread().getName() + " stop run");
    }

    /**
     * void parkUntil(long deadline) 可以单独使用
     */
    @Test
    void demoParkUntil() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start run");
            LockSupport.parkUntil(System.currentTimeMillis() + 2000);
            System.out.println(Thread.currentThread().getName() + " stop run");
        });
        t.start();
        t.join();
        System.out.println(Thread.currentThread().getName() + " stop run");
    }

    /**
     * void parkNanos(long nanos) 可以单独使用
     */
    @Test
    void demoParkNanos() throws InterruptedException {

        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start run");
            LockSupport.parkNanos(1000000000);
            System.out.println(Thread.currentThread().getName() + " stop run");
        });
        t.start();
        t.join();
        System.out.println(Thread.currentThread().getName() + " stop run");
    }

    /**
     * 调用线程的interrupt也会导致park被中断，但是差别是它不抛出InterruptedException
     */
    @Test
    void demoParkInterrupt() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start run");
            // 调用线程的interrupt也会导致park被中断，但是差别是它不抛出InterruptedException
            LockSupport.park(this);
            if (Thread.currentThread().isInterrupted()) {
                // 通常通过判断来弥补这个问题，当发生中断时执行自定义操作
                System.out.println(Thread.currentThread().getName() + " is interrupted, so do something");
                LockSupport.park(this);
                System.out.println(Thread.currentThread().getName() + " repark");
                LockSupport.park(this);
                System.out.println(Thread.currentThread().getName() + " repark");
            }
            System.out.println(Thread.currentThread().getName() + " stop run");
        });
        t.start();
        Thread.sleep(2000);
        // 调用线程的interrupt也会导致park被中断，但是差别是它不抛出InterruptedException
        t.interrupt();
        t.join();
        System.out.println(Thread.currentThread().getName() + " stop run");
    }

}
