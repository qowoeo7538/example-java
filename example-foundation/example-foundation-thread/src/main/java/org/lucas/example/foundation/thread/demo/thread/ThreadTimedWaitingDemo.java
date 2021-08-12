package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.util.ThreadTestUtils;

/**
 * 具有指定等待时间的等待线程的线程状态。
 */
class ThreadTimedWaitingDemo {


    @Test
    void demoJoinTime() {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " run over!");
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " run over!");
        });

        t1.start();
        t2.start();

        System.out.println(Thread.currentThread().getName() + " wait " + t1.getName() + " and " + t2.getName() + " run over!");

        try {
            //t1.join(1000);
            //t2.join(1000);
            t1.join(1000, 500);
            t2.join(1000, 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("final:" + t1.getName() + " and " + t2.getName() + " run over!");

        //查看线程状态
        System.out.println("t1's state:" + t1.getState());
        System.out.println("t2's state:" + t2.getState());

    }

    @Test
    void demoSleep() {
        final Object lock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " get Lock, sleeping");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " sleep over and run over!");
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " get Lock, sleeping");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " sleep over and run over!");
            }
        });

        t1.start();
        t2.start();

        t1.interrupt();

        ThreadTestUtils.complete(t1, t2);
    }

}
