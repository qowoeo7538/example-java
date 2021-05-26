package org.lucas.example.foundation.thread.kata.reentrant;

import org.junit.Test;
import org.lucas.example.foundation.thread.kata.reentrant.support.Child;

/**
 * 可重入锁
 */
public class ReentrantLockKata {

    @Test
    public void kataReentrantLock() throws Exception {
        var thread = new Thread(() -> {
            Child sub = new Child();
            sub.runChild();
        });
        thread.start();
        thread.join();
    }

}
