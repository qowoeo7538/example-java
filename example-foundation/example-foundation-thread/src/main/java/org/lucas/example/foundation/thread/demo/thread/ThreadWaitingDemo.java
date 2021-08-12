package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;

/**
 * 等待线程的线程状态,处于等待状态的线程正在等待另一个线程执行特定操作.
 */
class ThreadWaitingDemo {

    @Test
    void demoJoin() throws Exception {
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

        t1.join();
        t2.join();

        System.out.println("final:" + t1.getName() + " and " + t2.getName() + " run over!");

        //查看线程状态
        System.out.println("t1's state:" + t1.getState());
        System.out.println("t2's state:" + t2.getState());

    }

    @Test
    void demoJoinInterrupt1() throws Exception {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000 * 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " run over!");
        });

        Thread mainThread = Thread.currentThread();

        Thread t2 = new Thread(() -> {
            // 调用主线程的interrupt方法, 开启中断标记, 会影响主线中的join方法抛出异常,但是并不会阻碍t1线程的运行
            mainThread.interrupt();
            System.out.println(mainThread.getName() + " interrupt!");
            System.out.println(Thread.currentThread().getName() + " run over!");
        });

        t1.start();
        t2.start();

        System.out.println(Thread.currentThread().getName() + " wait " + t1.getName() + " and " + t2.getName() + " run over!");

        t1.join();

        System.out.println("final:" + t1.getName() + " and " + t2.getName() + " run over!");

        System.out.println("t1's state:" + t1.getState());
        System.out.println("t2's state:" + t2.getState());
        System.out.println("main's state:" + mainThread.getState());
    }

    @Test
    void demoJoinInterrupt2() throws Exception {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000 * 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " run over!");
        });

        Thread mainThread = Thread.currentThread();

        Thread t2 = new Thread(() -> {
            // 会影响t1线程的sleep方法抛出异常,让t1线程结束
            t1.interrupt();
            System.out.println(mainThread.getName() + " interrupt!");
            System.out.println(Thread.currentThread().getName() + " run over!");
        });

        t1.start();
        t2.start();

        System.out.println(Thread.currentThread().getName() + " wait " + t1.getName() + " and " + t2.getName() + " run over!");

        t1.join();

        System.out.println("final:" + t1.getName() + " and " + t2.getName() + " run over!");

        System.out.println("t1's state:" + t1.getState());
        System.out.println("t2's state:" + t2.getState());
        System.out.println("main's state:" + mainThread.getState());
    }

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

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
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
            }
        });

        //t1.interrupt();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " get Lock, t1.interrupt");
                    //Kevin提醒：中断t1线程
                    t1.interrupt();
                    System.out.println(Thread.currentThread().getName() + " release Lock,run over");
                }
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
