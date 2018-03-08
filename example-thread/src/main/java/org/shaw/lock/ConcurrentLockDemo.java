package org.shaw.lock;

import org.shaw.lock.impl.ConcurrentLockImpl;

/**
 *
 */
public class ConcurrentLockDemo {

    public static void main(String[] args) {
        ConcurrentLockImpl concurrentLock = new ConcurrentLockImpl();
        concurrentLock.tryLock("lockName", () ->
                System.out.println("doing ...")
        );
    }
}
