package org.lucas.example.foundation.thread.demo.lock.support.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class LockHoldCount {

    // 重入锁
    private ReentrantLock lock = new ReentrantLock();

    public void run1() {
        try {
            lock.lock();
            System.out.println("进入run1方法，holdCount数为：" + lock.getHoldCount());
            // 调用run2方法
            run2();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void run2() {
        try {
            lock.lock();
            System.out.println("进入run2方法，holdCount数为：" + lock.getHoldCount());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
