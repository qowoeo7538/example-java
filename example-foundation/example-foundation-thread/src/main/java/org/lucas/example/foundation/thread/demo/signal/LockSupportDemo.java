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
public class LockSupportDemo {

    @Test
    public void demoLockSupport() {
        // 1次 unpark 给线程许可
        LockSupport.unpark(Thread.currentThread());
        // 如果线程非阻塞重复调用没有任何效果
        LockSupport.unpark(Thread.currentThread());
        // 消耗一个许可
        LockSupport.park(Thread.currentThread());
        // 阻塞
        LockSupport.park(Thread.currentThread());
    }
}
