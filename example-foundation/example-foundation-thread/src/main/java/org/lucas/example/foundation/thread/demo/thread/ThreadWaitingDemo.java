package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.util.ThreadTestUtils;

import java.util.ArrayList;
import java.util.List;

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
    void demoWaiting() throws Exception {
        final Object lock = new Object();

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                synchronized (lock) {
                    System.out.println(" yield");
                    // 打开关闭此注释查看输出效果，对比差异
                    //this.yield();

                    // 使用wait方法来做对比，查看释放锁与不释放锁的区别
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(" run over");
                }
            });
            t.start();
            threadList.add(t);
        }

        Thread.sleep(1000);

        // 配合wait使用看效果
        synchronized (lock) {
            lock.notifyAll();
        }

        ThreadTestUtils.complete(threadList);
    }

}
