package org.shaw.lock.demo;

import org.shaw.lock.demo.impl.TestLock;

/**
 * 高效锁的实现
 * <p>
 * todo 重试机制
 */
public class ConcurrentLockDemo {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new TestLock()).start();
        }
    }
}
