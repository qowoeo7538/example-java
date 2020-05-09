package org.lucas.example.framework.spring.thread.impl;

import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Supplier;

// @EnableAsync
@Component
public class AsyncAnnotationExecutor {

    /**
     * 指定线程池异步执行
     */
    @Async("defaultExecutor")
    public void printMessages() {
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " message：" + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return 返回值类型必须是 Future 或者其子类类型
     * java.util.concurrent.Future
     * org.springframework.util.concurrent.ListenableFuture
     * java.util.concurrent.CompletableFuture
     * org.springframework.scheduling.annotation.AsyncResult
     */
    @Async("taskExecutor")
    public CompletableFuture<String> doSomething() {
        // 1. 创建 future
        CompletableFuture<String> result = new CompletableFuture<>();
        // 2. 模拟 任务 执行
        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "doSomething");
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.complete(" done");
        // 3. 返回结果
        return result;
    }

    @Async("defaultExecutor")
    public void callException() {
        throw new RuntimeException("模拟异常");
    }
}

/**
 * AsyncAnnotationExecutor 使用 @Async 代理后的
 * 默认 SimpleAsyncTaskExecutor 实现。
 */
class AsyncAnnotationExecutorProxy {

    private AsyncAnnotationExecutor asyncTask;

    private TaskExecutor executor = new SimpleAsyncTaskExecutor();

    public AsyncAnnotationExecutor getAsyncTask() {
        return asyncTask;
    }

    public void setAsyncAnnotationExecutor(AsyncAnnotationExecutor asyncTask) {
        this.asyncTask = asyncTask;
    }

    public CompletableFuture<String> doSomething() {
        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    return asyncTask.doSomething().get();
                } catch (Throwable e) {
                    throw new CompletionException(e);
                }
            }
        }, executor);
    }
}


