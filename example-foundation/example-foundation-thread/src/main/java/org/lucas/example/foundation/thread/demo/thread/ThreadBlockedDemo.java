package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;

public class ThreadBlockedDemo {

    @Test
    void demoWait() throws Exception {
        final Object lock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " get Lock, waiting 2000 million");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " release Lock,run over");
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                // 修改这个数值的大小,来观察t1线程wailt(timeout)的效果
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " get Lock, notify");
                lock.notify();
                System.out.println(Thread.currentThread().getName() + " release Lock,run over");
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("final:" + t1.getName() + " and " + t2.getName() + " run over!");
        System.out.println("t1's state:" + t1.getState());
        System.out.println("t2's state:" + t2.getState());
    }

    @Test
    void demoWaitInterrupted() throws Exception {
        final Object lock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " get Lock, waiting");
                try {
                    //Kevin提醒：如果达到2s还没有得到notify或者interrupt,则继续执行
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " release Lock,run over");
            }
        });

        //t1.interrupt();

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " get Lock, t1.interrupt");
                //Kevin提醒：中断t1线程
                t1.interrupt();
                System.out.println(Thread.currentThread().getName() + " release Lock,run over");
            }
        });

        t1.start();

        Thread.sleep(1000);

        t2.start();

        t1.join();
        t2.join();

        System.out.println("final:" + t1.getName() + " and " + t2.getName() + " run over!");
        System.out.println("t1's state:" + t1.getState());
        System.out.println("t2's state:" + t2.getState());
    }


}
