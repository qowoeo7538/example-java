package org.shaw.util.thread;

import org.shaw.util.Assert;
import org.shaw.util.thread.impl.SecurityTask;
import org.shaw.util.thread.impl.ThrottleSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @create: 2017-11-06
 * @description: 线程池
 */
public class DefaultThreadFactory {
    /**
     * 核心线程池大小
     * <p>
     * 当线程池小于corePoolSize时，新提交任务将创建一个新线程执行任务，
     * 即使此时线程池中存在空闲线程。
     * <p>
     * 当线程池达到corePoolSize时，新提交任务将被放入任务队列中.
     *
     * @see ThreadPoolTaskExecutor#corePoolSize
     */
    private static int corePoolSize = 10;

    /**
     * 最大线程池大小
     * <p>
     * 如果任务队列已满,将创建最大线程池的数量执行任务,如果超出最大线程池的大小,
     * 将提交给RejectedExecutionHandler处理
     *
     * @see ThreadPoolTaskExecutor#maxPoolSize
     */
    private static int maxPoolSize = 10;

    /**
     * 线程处理控制
     */
    private static ThrottleSupport throttleSupport = new ThrottleSupport();

    /**
     * 阻塞任务队列容量(默认为int的最大值)
     *
     * @see ThreadPoolTaskExecutor#queueCapacity
     */
    private static int queueCapacity = 15;

    /** 线程池对象 */
    private static ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    // 初始化线程对象
    static {
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.initialize();
    }

    public static void destroy() {
        Assert.state(threadPoolTaskExecutor != null, "线程池没有初始化");
        throttleSupport.awaitFinish();
        threadPoolTaskExecutor.destroy();
    }

    public static void execute(Runnable task) {
        Assert.state(threadPoolTaskExecutor != null, "线程池没有初始化");
        throttleSupport.beforeAccess();
        threadPoolTaskExecutor.execute(new SecurityTask(task, throttleSupport));
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() throws IllegalStateException {
        Assert.state(threadPoolTaskExecutor != null, "线程池没有初始化");
        return threadPoolTaskExecutor.getThreadPoolExecutor();
    }
}