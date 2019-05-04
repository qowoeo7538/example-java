package org.lucas.demo.semaphore;

import org.lucas.core.task.ExampleThreadExecutor;
import org.lucas.util.DataProducerHelper;

import java.util.concurrent.Semaphore;

/**
 * @create: 2018-01-03
 * @description:
 */
public class SemaphoreDemo {

    /** 并发控制 */
    private final static int concurrent = 5;

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(concurrent);
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(() -> {
                try {
                    System.out.println("[" + Thread.currentThread().getId() + "]等待获取许可！");
                    semaphore.acquire();
                    System.out.println("[" + Thread.currentThread().getId() + "]已经获取许可！");
                    Thread.sleep((long) DataProducerHelper.nextInt(1, 10000));
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