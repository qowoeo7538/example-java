package org.lucas.example.foundation.thread.kata.safe;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.thread.kata.safe.impl.TestLock;

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
