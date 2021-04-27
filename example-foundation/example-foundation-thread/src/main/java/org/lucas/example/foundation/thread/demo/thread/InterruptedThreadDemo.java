package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.component.thread.task.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class InterruptedThreadDemo {

    public static ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    @Test
    public void runWithTimeout() throws TimeoutException, InterruptedException {
        Future<?> future = executor.submit(() -> {
            try {
                System.out.println("开始任务");
                for (int i = 0; i < 1000000; i++) {
                    Thread.yield();
                }
                System.out.println("无异常执行");
            } catch (final Exception e) {
                System.out.println("停止异常");
                Thread.currentThread().interrupt();
            }
            System.out.println("完成");
        });
        try {
            future.get(1L, TimeUnit.MICROSECONDS);
        } catch (InterruptedException | TimeoutException e) {
            future.cancel(true);
            throw e;
        } catch (ExecutionException e) {
            throw new AssertionError();
        }
    }

}
