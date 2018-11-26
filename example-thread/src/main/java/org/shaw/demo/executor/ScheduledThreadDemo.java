package org.shaw.demo.executor;

import org.shaw.demo.executor.impl.ScheduledTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-17
 * @description: 延时执行器
 */
public class ScheduledThreadDemo {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor service = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        for (int i = 0; i < 5; i++) {
            service.schedule(new ScheduledTask(1, 100), i, TimeUnit.SECONDS);
        }
        service.shutdown();
        try {
            service.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
