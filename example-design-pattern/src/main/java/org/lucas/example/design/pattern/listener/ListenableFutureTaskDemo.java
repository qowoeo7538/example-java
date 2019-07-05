package org.lucas.example.design.pattern.listener;

import org.junit.jupiter.api.Test;
import org.lucas.core.Constants;
import org.lucas.task.ThreadPoolTaskExecutor;
import org.lucas.example.core.util.DataProducerHelper;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-24
 * @description:
 */
public class ListenableFutureTaskDemo {

    private static final int DEFAULT_CORE_POOL_SIZE = Constants.CORE_SIZE + 1;
    private static final int DEFAULT_MAX_POOL_SIZE = 2 * Constants.CORE_SIZE + 1;
    private static final int DEFAULT_KEEP_ALIVE_SECONDS = 60;
    private static final int DEFAULT_QUEUE_CAPACITY = Integer.MAX_VALUE - DEFAULT_MAX_POOL_SIZE;

    private static final ExecutorService threadPoolExecutor =
            new ThreadPoolTaskExecutor(DEFAULT_CORE_POOL_SIZE,
                    DEFAULT_MAX_POOL_SIZE,
                    DEFAULT_KEEP_ALIVE_SECONDS,
                    DEFAULT_QUEUE_CAPACITY);

    @Test
    public void listenableFutureTask() {
        final TaskExecutorAdapter taskExecutorAdapter = new TaskExecutorAdapter(threadPoolExecutor);
        try {
            final ListenableFuture listenableFuture = taskExecutorAdapter.submitListenable(() -> {
                System.out.println("任务开始");
                try {
                    TimeUnit.SECONDS.sleep(DataProducerHelper.nextInt(10));
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("任务结束");
            });
            final CompletableFuture completableFuture = listenableFuture.completable();
            completableFuture.get();
        } catch (final ExecutionException | InterruptedException ex) {
            Thread.currentThread().interrupt();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

}
