package org.shaw.kata.lock.impl;

import org.shaw.demo.lock.impl.Process;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

/**
 * 高效锁的一种实现方式
 */
public class ConcurrentLockImpl {

    private final ConcurrentMap<String, CountDownLatch> cachedLock = new ConcurrentHashMap<>();

    public void tryLock(String name, Process process) {
        CountDownLatch signal = cachedLock.putIfAbsent(name, new CountDownLatch(1));
        if (signal == null) {
            signal = cachedLock.get(name);
            try {
                // 具体业务
                process.process();
            } finally {
                signal.countDown();
                cachedLock.remove(name);
            }
        } else {
            try {
                signal.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}

