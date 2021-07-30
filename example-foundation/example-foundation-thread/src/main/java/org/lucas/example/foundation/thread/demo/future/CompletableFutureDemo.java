package org.lucas.example.foundation.thread.demo.future;

import org.junit.jupiter.api.Test;
import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.core.util.DataProducerHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

/**
 * 执行方法：
 * complete()：显示设置结果，可以使 CompletableFuture 立即获得结果,不必等待整个线程完成任务。
 * runAsync()：无返回值的异步计算
 * supplyAsync()：有返回值的异步执行
 * thenApplyAsync()：获取上一个任务的执行结果进行执行，并返回执行结果。
 * thenAcceptAsync()：注册一个任务，会在前一个操作完成后根据结果继续执行，但无返回值。
 * thenRunAsync()：与 thenAcceptAsync 不同，并不使用前一个操作的计算的结果进行下次执行。
 * whenCompleteAsync()：设置回调函数，当异步任务执行完毕后进行回调，不会阻塞调用线程：
 * <p>
 * 结果方法：
 * get(): 阻塞获取结果，实现Future的get接口，显式抛出异常.
 * getNow(T valueIfAbsent): 立即获取执行结果，如果当前任务未执行完成，则返回valueIfAbsent.
 * get(long timeout, TimeUnit unit): 在有限时间内获取数据.
 * join(): 任务完成后返回执行结果，或者抛出 unchecked 异常,不会抛出任何检测到的异常.
 */
public class CompletableFutureDemo {

    @Test
    public void demoComplete() throws Exception {
        final CompletableFuture<String> future = new CompletableFuture<>();
        ExampleThreadExecutor.submit(() -> {
            System.out.println("task doing...");
            try {
                // int i = 1 / 0; 当发生无法捕获的异常,get()方法会进行阻塞
                Thread.sleep(DataProducerHelper.nextInt(1000, 5000));
                // 设置计算结果，主线程调用方法会立即收到信息
                future.complete("1.success");
                Thread.sleep(DataProducerHelper.nextInt(1000, 5000));
                System.out.println("2.释放资源");
                Thread.sleep(DataProducerHelper.nextInt(1000, 5000));
                System.out.println("3.释放完毕");
            } catch (final Exception e) {
                future.completeExceptionally(e);
            }
        });

        // 如果future的结果没有被设置，则主线程将一直被阻塞，
        // 执行 complete 设置完结果后，所有因 get() 阻塞的线程将被唤醒，并返回结果。
        System.out.println(future.get());

        ExampleThreadExecutor.destroy();
    }

    @Test
    public void demoRunAsync() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        CompletableFuture.runAsync(() -> {
            System.out.println("over :" + dtf.format(LocalDateTime.now()));
            throw new IllegalArgumentException("illegal exception!");
        }, new ThreadPoolTaskExecutor())
                .exceptionally((e) -> {
                    // 当原始的 CompletableFuture 抛出异常的时候，就会触发这个对象的计算。
                    System.out.println(e.getMessage());
                    return null;
                })
                // 设置回调函数，当异步任务执行完毕后进行回调，不会阻塞调用线程：
                .whenCompleteAsync((t, u) -> System.out.println("complete, exception:" + u), new ThreadPoolTaskExecutor())
                // 挂起当前线程，等待异步任务执行完毕.
                .join();
    }

    @Test
    public void demoSupplyAsync() {
        // 设置日期格式
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

        CompletableFuture<String> future = CompletableFuture
                // 任务 A
                .supplyAsync(() -> dtf.format(LocalDateTime.now()), new ThreadPoolTaskExecutor())
                // 任务B。任务A执行完毕后，激活任务B，并获取A的执行结果。
                .thenApplyAsync(result -> "当前时间: " + result, new ThreadPoolTaskExecutor())
                .handleAsync((v, e) -> "value is = " + v + " && exception is =  " + e);
        System.out.println(future.join());
    }

    @Test
    public void demoAccept() {
        // 任务 A
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
            // 任务B 。任务A，执行完毕后，激活异步任务B执行，这种方式激活的异步任务B是可以拿到任务A的执行结果
        }).thenAcceptAsync(System.out::println, new ThreadPoolTaskExecutor()).join();
    }

    @Test
    public void demoRun() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("执行CompletableFuture");
            return "first";
        }).thenRunAsync(() -> System.out.println("finished"), new ThreadPoolTaskExecutor())
                .join();
    }

}
