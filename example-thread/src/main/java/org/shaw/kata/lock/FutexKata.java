package org.shaw.kata.lock;

import org.junit.Test;
import org.shaw.core.task.ExampleThreadExecutor;
import org.shaw.kata.lock.impl.TestLock;

/**
 * 锁的一种实现
 * <p>
 * todo 重试机制
 */
public class FutexKata {

    private final int index = 0;

    @Test
    public void lockTest() {
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(new TestLock());
        }
        ExampleThreadExecutor.destroy();
    }
}
