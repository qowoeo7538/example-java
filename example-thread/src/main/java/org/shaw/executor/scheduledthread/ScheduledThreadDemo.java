package org.shaw.executor.scheduledthread;

import org.shaw.executor.scheduledthread.impl.Task;

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
            service.schedule(new Task(1, 100), i, TimeUnit.SECONDS);
        }
        service.shutdown();
        try {
            service.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
