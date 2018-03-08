package org.shaw.lock.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

/**
 * 高效锁的一种实现方式
 */
public class ConcurrentLockImpl {
    private Collection<String> cachedLock = new HashSet<>();

    private ConcurrentMap<String, CountDownLatch> cacheTimestamp = new ConcurrentHashMap<>();

    public void tryLock(String name, Process process) {
        CountDownLatch signal = cacheTimestamp.putIfAbsent(name, new CountDownLatch(1));
        if (signal == null) {
            signal = cacheTimestamp.get(name);
            try {
                if (!cachedLock.contains(name)) {
                    // 具体实现
                    process.process();
                    cachedLock.add(name);
                }
            } finally {
                signal.countDown();
                cacheTimestamp.remove(name);
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

