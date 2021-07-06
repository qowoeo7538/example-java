package org.lucas.example.foundation.thread.kata.lock.support;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

/**
 * 高效锁的一种实现方式
 */
public class ConcurrentLockImpl {

    private final ConcurrentMap<String, CountDownLatch> cachedLock = new ConcurrentHashMap<>();

    public void lock(String name, Process process) {
        CountDownLatch latch = cachedLock.putIfAbsent(name, new CountDownLatch(1));
        if (latch == null) {
            latch = cachedLock.get(name);
            try {
                // 具体业务
                process.process();
            } finally {
                latch.countDown();
                cachedLock.remove(name);
            }
        } else {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void lock() {

    }

    public void unlock() {

    }

    public boolean tryLock(String name) {
        return false;
    }

}

