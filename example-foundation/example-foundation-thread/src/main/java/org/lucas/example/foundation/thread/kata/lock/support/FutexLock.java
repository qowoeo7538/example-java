package org.lucas.example.foundation.thread.kata.lock.support;

/**
 * @create: 2018-03-09
 * @description:
 */
public class FutexLock implements Runnable {

    public int i;

    private static final ConcurrentLockImpl concurrentLock = new ConcurrentLockImpl();

    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            concurrentLock.lock("testLock", () -> {
                i++;
                System.out.println(i);
            });
        }
    }
}
