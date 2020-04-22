package org.lucas.example.foundation.thread.demo.future.completable;

import org.junit.jupiter.api.Test;
import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异常方法：如果不处理，会导致调用线程一直阻塞
 * completeExceptionally：将内部异常抛出
 * exceptionally：异常发生时触发该方法
 */
public class ExceptionFutureDemo {

    @Test
    public void demoCompleteExceptionally() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = new CompletableFuture<>();
        ExampleThreadExecutor.submit(() -> {
            future.completeExceptionally(new RuntimeException("exception"));
        });
        System.out.println(future.get());
    }

    @Test
    public void demoExceptionally() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = new CompletableFuture<>();
        ExampleThreadExecutor.submit(() -> {
            future.completeExceptionally(new RuntimeException("exception"));
        });
        System.out.println(future.exceptionally(t -> t.toString()).get());
    }

    @Test
    public void demoCombineException() throws InterruptedException, ExecutionException {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("occur exception");
        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> "second"), (x, y) -> y + ":" + x, new ThreadPoolTaskExecutor());
        System.out.println(future.get());
    }

}
