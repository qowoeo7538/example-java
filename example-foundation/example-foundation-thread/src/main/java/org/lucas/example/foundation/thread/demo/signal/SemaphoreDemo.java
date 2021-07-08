package org.lucas.example.foundation.thread.demo.signal;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.core.util.DataProducerHelper;

import java.util.concurrent.Semaphore;

/**
 * @create: 2018-01-03
 * @description:
 */
public class SemaphoreDemo {

    @Test
    public void demoSemaphore() {
        final int concurrent = 5;
        Semaphore semaphore = new Semaphore(concurrent);
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(() -> {
                try {
                    System.out.println("[" + Thread.currentThread().getId() + "]等待获取许可！");
                    semaphore.acquire();
                    System.out.println("[" + Thread.currentThread().getId() + "]已经获取许可！");
                    Thread.sleep(DataProducerHelper.nextInt(1, 10000));
                    semaphore.release();
                    System.out.println("[" + Thread.currentThread().getId() + "]完成业务！");
                } catch (InterruptedException ix) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        ExampleThreadExecutor.destroy();
    }



}
