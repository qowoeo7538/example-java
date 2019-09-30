package org.lucas.example.core.task;

import org.lucas.component.common.core.constants.SystemConstants;
import org.lucas.component.thread.task.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ExampleThreadExecutor {

    private static final int DEFAULT_CORE_POOL_SIZE = SystemConstants.CORE_SIZE + 1;
    private static final int DEFAULT_MAX_POOL_SIZE = 2 * SystemConstants.CORE_SIZE + 1;
    private static final int DEFAULT_KEEP_ALIVE_SECONDS = 60;
    private static final int DEFAULT_QUEUE_CAPACITY = Integer.MAX_VALUE - DEFAULT_MAX_POOL_SIZE;

    private static final ThreadPoolTaskExecutor threadPoolExecutor =
            new ThreadPoolTaskExecutor(DEFAULT_CORE_POOL_SIZE,
                    DEFAULT_MAX_POOL_SIZE,
                    DEFAULT_KEEP_ALIVE_SECONDS,
                    DEFAULT_QUEUE_CAPACITY);

    /**
     * 是否需要所有任务完成.
     */
    private static boolean isWait = false;

    /**
     * 是否需要立即关闭.
     */
    private static boolean waitForTasksToCompleteOnShutdown = false;

    private static final Object monitor = new Object();

    static {
        setWaitForTasksToCompleteOnShutdown(true);
        threadPoolExecutor.setAfter(((runnable, throwable) -> {
            if (isWait && threadPoolExecutor.getTaskCount() == 0) {
                synchronized (monitor) {
                    monitor.notify();
                }
            }
        }));
    }

    private ExampleThreadExecutor() {
    }

    public static void setWaitForTasksToCompleteOnShutdown(final boolean waitForJobsToCompleteOnShutdown) {
        waitForTasksToCompleteOnShutdown = waitForJobsToCompleteOnShutdown;
    }

    public static void execute(Runnable task) {
        threadPoolExecutor.execute(task);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return threadPoolExecutor.submit(task);
    }

    public static <T> Future<T> submit(Callable<T> task, T defaultValue) {
        return threadPoolExecutor.submit(task, defaultValue);
    }

    public static Future<?> submit(Runnable task) {
        return threadPoolExecutor.submit(task);
    }

    public static void destroy() {
        waitFinish();
        if (waitForTasksToCompleteOnShutdown) {
            threadPoolExecutor.shutdown();
        } else {
            threadPoolExecutor.shutdownNow();
        }
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() throws IllegalStateException {
        return threadPoolExecutor.getThreadPoolExecutor();
    }

    private static void waitFinish() {
        isWait = true;
        synchronized (monitor) {
            while (threadPoolExecutor.getTaskCount() > 0) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}