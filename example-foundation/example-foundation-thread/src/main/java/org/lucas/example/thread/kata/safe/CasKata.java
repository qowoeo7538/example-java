package org.lucas.example.thread.kata.safe;

import org.junit.jupiter.api.Test;
import org.lucas.example.core.task.ExampleThreadExecutor;
import org.lucas.example.thread.kata.safe.impl.CasThread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS：乐观锁的一种，通过判断当前值是否符合预期值来决定是否进行修改。
 */
public class CasKata {

    private static final AtomicInteger COUNT = new AtomicInteger(0);

    @Test
    public void casTest(){
        for (int j = 0; j < 100; j++) {
            ExampleThreadExecutor.execute(new CasThread(COUNT));
        }
        ExampleThreadExecutor.destroy();

        System.out.println(COUNT.get());
    }
}
