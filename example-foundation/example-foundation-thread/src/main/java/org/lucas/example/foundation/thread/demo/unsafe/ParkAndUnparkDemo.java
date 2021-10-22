package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

import static java.util.concurrent.locks.LockSupport.unpark;

class ParkAndUnparkDemo {

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

}
