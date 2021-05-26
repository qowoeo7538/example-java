package org.lucas.example.foundation.thread.demo.lock;

import org.junit.Test;
import org.lucas.example.foundation.thread.demo.lock.support.Child;

/**
 * 可重入锁,同一线程可以再次获取同一对象的锁
 */
public class ReentrantLockDemo {

    @Test
    public void demoReentrantLock() throws Exception {
        var thread = new Thread(() -> {
            Child sub = new Child();
            sub.runChild();
        });
        thread.start();
        thread.join();
    }

}
