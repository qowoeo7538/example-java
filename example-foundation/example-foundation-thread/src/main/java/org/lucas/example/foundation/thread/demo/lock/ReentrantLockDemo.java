package org.lucas.example.foundation.thread.demo.lock;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.thread.demo.lock.support.ChangeLock;
import org.lucas.example.foundation.thread.demo.lock.support.reentrantlock.LockHoldCount;
import org.lucas.example.foundation.thread.demo.lock.support.reentrantlock.ReentrantLockThread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁,同一线程可以再次获取同一对象的锁
 */
class ReentrantLockDemo {

    @Test
    void demo() {
        final ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(new ReentrantLockThread(lock));
        }
        ExampleThreadExecutor.destroy();
    }

    /**
     * 支持可重入
     */
    @Test
    void demoReentrantLock() {
        System.out.println("ReentrantLock:重入锁对比");
        ReentrantLock rl = new ReentrantLock();
        rl.lock();
        System.out.println("get ReentrantLock lock1");
        rl.lock();
        System.out.println("get ReentrantLock lock2");
        rl.unlock();
    }

    @Test
    void demoAQS() throws InterruptedException {
        ReentrantLock rl = new ReentrantLock();
        System.out.println(Thread.currentThread().getName() + " start rl.lock");
        rl.lock();
        rl.lock();

        Thread th = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start rl.lock");
            System.out.println("start re lock2");
            rl.lock();
        });

        th.start();
        th.join();
        rl.unlock();
        System.out.println(Thread.currentThread().getName() + " start rl.unlock");
        System.out.println("main is over");
    }

    /**
     * 在线程中修改锁对象的引用，会导致锁失效。
     * 在线程中修改了锁对象的属性,只要不修改引用则不会产生线程安全问题.
     */
    @Test
    void demoChangeLock() throws Exception {
        final ChangeLock changeLock = new ChangeLock();
        Thread t1 = new Thread(changeLock::method, "t1");
        Thread t2 = new Thread(changeLock::method, "t2");
        t1.start();
        try {
            Thread.sleep(100);
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }
        //由于锁的引用被改变，所以t2线程也进入到method方法内执行。
        t2.start();
        t1.join();
        t2.join();
    }

    /**
     * 获取可冲入锁的持有个数
     */
    @Test
    void demoHoldCount() {
        LockHoldCount thc = new LockHoldCount();
        thc.run1();
    }

}
