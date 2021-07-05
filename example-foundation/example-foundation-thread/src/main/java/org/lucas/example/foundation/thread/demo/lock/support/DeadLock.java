package org.lucas.example.foundation.thread.demo.lock.support;

public class DeadLock {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void execute1() {
        synchronized (lock1) {
            System.out.println("线程" + Thread.currentThread().getName() + "获得lock1执行execute1开始");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                System.out.println("线程" + Thread.currentThread().getName() + "获得lock2执行execute1开始");
            }
        }
    }

    public void execute2() {
        synchronized (lock2) {
            System.out.println("线程" + Thread.currentThread().getName() + "获得lock2执行execute2开始");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1) {
                System.out.println("线程" + Thread.currentThread().getName() + "获得lock1执行execute2开始");
            }
        }
    }

}
