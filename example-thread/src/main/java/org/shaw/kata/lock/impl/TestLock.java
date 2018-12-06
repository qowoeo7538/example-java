package org.shaw.kata.lock.impl;

/**
 * @create: 2018-03-09
 * @description:
 */
public class TestLock implements Runnable {

    public static int i;

    private final static ConcurrentLockImpl concurrentLock = new ConcurrentLockImpl();

    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            concurrentLock.tryLock("testLock", () -> {
                i++;
                System.out.println(i);
            });
        }
    }
}
