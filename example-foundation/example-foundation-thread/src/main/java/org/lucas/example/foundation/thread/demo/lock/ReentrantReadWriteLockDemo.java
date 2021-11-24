package org.lucas.example.foundation.thread.demo.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReentrantReadWriteLockDemo {

    /**
     * 支持可重入读写锁
     */
    @Test
    void demoReentrantLock() {
        System.out.println("ReentrantReadWriteLock:重入锁对比");
        ReentrantReadWriteLock rrw = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = rrw.writeLock();
        writeLock.lock();
        System.out.println("get ReentrantReadWriteLock.WriteLock lock1");
        writeLock.lock();
        System.out.println("get ReentrantReadWriteLock.WriteLock lock2");
        writeLock.unlock();
    }

}
