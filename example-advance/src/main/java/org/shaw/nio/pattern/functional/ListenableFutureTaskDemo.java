package org.shaw.nio.pattern.functional;

import org.shaw.util.DataProducer;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.*;

/**
 * @create: 2017-11-24
 * @description:
 */
public class ListenableFutureTaskDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        TaskExecutorAdapter taskExecutorAdapter = new TaskExecutorAdapter(executor);
        try {
            ListenableFuture listenableFuture = taskExecutorAdapter.submitListenable(() -> {
                System.out.println("任务开始");
                try {
                    TimeUnit.SECONDS.sleep(DataProducer.nextInt(10));
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("任务结束");
            });
            CompletableFuture completableFuture = listenableFuture.completable();
            completableFuture.get();
        } catch (ExecutionException | InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }
}
