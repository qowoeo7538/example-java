package org.shaw.core.task;

import org.shaw.core.Constants;
import org.shaw.task.StandardThreadExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * StandardThreadExecutor execute执行策略：	优先扩充线程到maxThread，再offer到queue，如果满了就reject
 * <p>
 * 适应场景：  比较适合于业务处理需要远程资源的场景
 */
public class ExampleThreadExecutor {

    private static final int DEFAULT_CORE_POOL_SIZE = Constants.CORE_SIZE + 1;
    private static final int DEFAULT_MAX_POOL_SIZE = 2 * Constants.CORE_SIZE + 1;
    private static final int DEFAULT_KEEP_ALIVE_SECONDS = 60;
    private static final int DEFAULT_QUEUE_CAPACITY = Integer.MAX_VALUE - DEFAULT_MAX_POOL_SIZE;

    private static final StandardThreadExecutor threadPoolExecutor =
            new StandardThreadExecutor(DEFAULT_CORE_POOL_SIZE,
                    DEFAULT_MAX_POOL_SIZE,
                    DEFAULT_KEEP_ALIVE_SECONDS,
                    DEFAULT_QUEUE_CAPACITY);

    /**
     * 是否需要所有任务完成
     */
    private static boolean isWait = false;

    private static final Object monitor = new Object();

    static {
        threadPoolExecutor.setWaitForTasksToCompleteOnShutdown(true);
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
        threadPoolExecutor.destroy();
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