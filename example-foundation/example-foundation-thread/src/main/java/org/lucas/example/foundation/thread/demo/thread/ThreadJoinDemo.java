package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;

class ThreadJoinDemo {

    @Test
    void demoJoin() {
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
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("final:" + t1.getName() + " and " + t2.getName() + " run over!");

        //查看线程状态
        System.out.println("t1's state:" + t1.getState());
        System.out.println("t2's state:" + t2.getState());

    }

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
    void demoJoinInterrupt1() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000 * 2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " run over!");
            }
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

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("final:" + t1.getName() + " and " + t2.getName() + " run over!");

        System.out.println("t1's state:" + t1.getState());
        System.out.println("t2's state:" + t2.getState());
        System.out.println("main's state:" + mainThread.getState());
    }

    @Test
    void demoJoinInterrupt2() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000 * 2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " run over!");
            }
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

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("final:" + t1.getName() + " and " + t2.getName() + " run over!");

        System.out.println("t1's state:" + t1.getState());
        System.out.println("t2's state:" + t2.getState());
        System.out.println("main's state:" + mainThread.getState());
    }

}
