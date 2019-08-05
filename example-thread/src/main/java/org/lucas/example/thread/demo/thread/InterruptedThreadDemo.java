package org.lucas.example.thread.demo.thread;

import org.lucas.component.thread.task.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class InterruptedThreadDemo {

    public static ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    public static void main(String[] args) throws TimeoutException, InterruptedException {
        runWithTimeout(() -> {
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
        }, 1L, TimeUnit.MICROSECONDS);
    }

    public static void runWithTimeout(final Runnable runnable, final long timeoutDuration, final TimeUnit timeoutUnit)
            throws TimeoutException, InterruptedException {
        Future<?> future = executor.submit(runnable);
        try {
            future.get(timeoutDuration, timeoutUnit);
        } catch (InterruptedException | TimeoutException e) {
            future.cancel(true);
            throw e;
        } catch (ExecutionException e) {
            throw new AssertionError();
        }
    }

}
