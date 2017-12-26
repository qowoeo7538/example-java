package org.shaw.core.task;

import org.shaw.core.task.support.ThrottleSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * StandardThreadExecutor execute执行策略：	优先扩充线程到maxThread，再offer到queue，如果满了就reject
 * <p>
 * 适应场景：  比较适合于业务处理需要远程资源的场景
 */
public class StandardThreadExecutor {

    private StandardThreadExecutor() {

    }

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
    private static int CORE_POOL_SIZE = 4;

    /**
     * 最大线程池大小
     * <p>
     * 如果任务队列已满,将创建最大线程池的数量执行任务,如果超出最大线程池的大小,
     * 将提交给RejectedExecutionHandler处理
     *
     * @see ThreadPoolTaskExecutor#maxPoolSize
     */
    private static int MAX_POOL_SIZE = 4;

    /**
     * 阻塞任务队列容量(默认为int的最大值)
     *
     * @see ThreadPoolTaskExecutor#queueCapacity
     */
    private static int QUEUE_CAPACITY = 15;

    /**
     * 线程池中超过核心线程数目的空闲线程最大存活时间；
     * 可以allowCoreThreadTimeOut(true)使得核心线程有效时间
     */
    private static final int DEFAULT_KEEP_ALIVE_SECONDS = 60;

    /** 线程池对象 */
    private final static ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor() {
        @Override
        protected BlockingQueue<Runnable> createQueue(int queueCapacity) {
            ExecutorQueue executorQueue = new ExecutorQueue();
            return executorQueue;
        }
    };

    /**
     * 线程处理控制
     */
    private final static ConcurrencyThrottleAdapter throttleSupport = new ConcurrencyThrottleAdapter();

    // 初始化线程对象
    static {
        threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        threadPoolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        threadPoolTaskExecutor.setKeepAliveSeconds(DEFAULT_KEEP_ALIVE_SECONDS);
        threadPoolTaskExecutor.setThreadFactory(threadPoolTaskExecutor.getThreadPoolExecutor().getThreadFactory());
        threadPoolTaskExecutor.setRejectedExecutionHandler(threadPoolTaskExecutor.getThreadPoolExecutor().getRejectedExecutionHandler());
        // 设置最大并发数 maxPoolSize + queueCapacity
        throttleSupport.setConcurrencyLimit(MAX_POOL_SIZE + QUEUE_CAPACITY);
        threadPoolTaskExecutor.initialize();
    }

    public static void execute(Runnable task) {
        Assert.state(threadPoolTaskExecutor != null, "线程池没有初始化");
        throttleSupport.before(task);
        threadPoolTaskExecutor.execute(() -> {
            try {
                task.run();
            } catch (RejectedExecutionException rx) {
                // 尝试放入队列，失败则直接使用失败策略
                if (!((ExecutorQueue) getThreadPoolExecutor().getQueue()).force(task)) {
                    getThreadPoolExecutor().getRejectedExecutionHandler().rejectedExecution(task, getThreadPoolExecutor());
                }
            } finally {
                throttleSupport.after();
            }
        });
    }

    public static void destroy() {
        Assert.state(threadPoolTaskExecutor != null, "线程池没有初始化");
        throttleSupport.setDestroy(true);
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() throws IllegalStateException {
        Assert.state(threadPoolTaskExecutor != null, "线程池没有初始化");
        return threadPoolTaskExecutor.getThreadPoolExecutor();
    }

    private static class ConcurrencyThrottleAdapter extends ThrottleSupport {

        /** 并发量设置 */
        private int concurrencyLimit;

        /** Executor */
        private ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        private boolean isDestroy = false;

        /**
         * 运行前
         * <p>
         * 默认队列没有长度限制，因此这里进行控制
         *
         * @see ThrottleSupport#beforeAccess()
         */
        protected void before(Runnable task) {
            int count = super.beforeAccess();
            // 因为队列没有长度，所以在这里进行并发控制
            if (count > concurrencyLimit) {
                getConcurrencyCount().decrementAndGet();
                /**
                 * @see java.util.concurrent.ThreadPoolExecutor.AbortPolicy#rejectedExecution(Runnable, ThreadPoolExecutor)
                 */
                threadPoolExecutor.getRejectedExecutionHandler().rejectedExecution(task, threadPoolExecutor);
            }
        }

        /**
         * 运行后
         *
         * @see ThrottleSupport#afterAccess()
         */
        protected void after() {
            int count = super.afterAccess();
            if (isDestroy == true && count == 0) {
                threadPoolTaskExecutor.destroy();
            }
        }

        /**
         * 设置销毁
         *
         * @param destroy
         */
        public void setDestroy(boolean destroy) {
            isDestroy = destroy;
        }

        /**
         * 设置最大并发
         *
         * @param concurrencyLimit
         */
        protected void setConcurrencyLimit(int concurrencyLimit) {
            this.concurrencyLimit = concurrencyLimit;
        }
    }

    /**
     * {@code BlockingQueue} 存取锁，会导致性能底下，
     * 通过 {@code LinkedTransferQueue} 预占模式，保证更好的性能
     */
    private static class ExecutorQueue extends LinkedTransferQueue<Runnable> {

        public ExecutorQueue() {
            super();
        }

        /**
         * 将任务放入队列
         *
         * @param task
         * @return
         */
        public boolean force(Runnable task) {
            if (StandardThreadExecutor.getThreadPoolExecutor().isShutdown()) {
                throw new RejectedExecutionException("Executor没有运行，不能加入到队列");
            }
            return offer(task);
        }

        /**
         * 优先扩充线程到maxThread
         *
         * @param task
         * @return
         */
        @Override
        public boolean offer(Runnable task) {
            // 当前线程数量
            int poolSize = StandardThreadExecutor.getThreadPoolExecutor().getPoolSize();

            if (poolSize == StandardThreadExecutor.getThreadPoolExecutor().getMaximumPoolSize()) {
                // 当前线程数量是最大时，将任务加入队列
                return super.offer(task);
            }
            if (throttleSupport.getConcurrencyCount().get() <= poolSize) {
                // 当前任务数量等于当前线程数量，将任务加入队列
                return super.offer(task);
            }
            if (poolSize < StandardThreadExecutor.getThreadPoolExecutor().getMaximumPoolSize()) {
                // 当前线程数量小于最大线程数量，不加入队列。
                return false;
            }
            return super.offer(task);
        }
    }

}