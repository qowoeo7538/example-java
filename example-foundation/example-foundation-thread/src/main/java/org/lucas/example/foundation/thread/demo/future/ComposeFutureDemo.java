package org.lucas.example.foundation.thread.demo.future;

import org.junit.jupiter.api.Test;
import org.lucas.component.thread.task.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * thenComposeAsync()：第一个操作完成时，将其结果作为参数传递给第二个操作继续运行。
 * thenCombineAsync()：将两个 CompletableFuture 对象的执行结果整合起来，并且执行是并行的。
 * <p>
 * allOf()：将多个 CompletableFuture 合并，并等待所有操作执行完毕返回 null。
 * anyOf()：将多个 CompletableFuture 合并，当任意一个 CompletableFuture 执行完后就返回。
 */
public class ComposeFutureDemo {

    /**
     * 返回的对象并不一是函数fn返回的对象，如果原来的CompletableFuture还没有计算出来，
     * 它就会生成一个新的组合后的 CompletableFuture。
     */
    @Test
    public void demoCompose() {
        int f = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "first";
        }).thenComposeAsync(str -> CompletableFuture.supplyAsync(() -> {
            String str2 = "second";
            return str.length() + str2.length();
        }), new ThreadPoolTaskExecutor())
                .join();
        System.out.println("字符串长度为：" + f);
    }

    @Test
    public void demoCombine() {
        String f = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "first";
        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> "second"), (x, y) -> y + ":" + x, new ThreadPoolTaskExecutor())
                .join();
        System.out.println(f);
    }

    /**
     * thenAcceptBoth：将两个操作的计算结果合并运行一次。
     */
    @Test
    public void demoAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "first";
        }).thenAcceptBothAsync(CompletableFuture.completedFuture("second"),
                (first, second) -> System.out.println(first + " : " + second)
        ).join();
    }

    @Test
    public void demoAllOf() throws InterruptedException, ExecutionException {
        List<CompletableFuture<String>> futureList = new ArrayList<>() {{
            add(CompletableFuture.supplyAsync(() -> {
                System.out.println("1");
                return "1";
            }));
            add(CompletableFuture.supplyAsync(() -> {
                System.out.println("2");
                return "2";
            }));
            add(CompletableFuture.supplyAsync(() -> {
                System.out.println("3");
                return "3";
            }));
        }};
        CompletableFuture<Void> result = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
        System.out.println(result.get());
    }

    @Test
    public void demoAnyAll() throws InterruptedException, ExecutionException {
        List<CompletableFuture<String>> futureList = new ArrayList<>() {{
            add(CompletableFuture.supplyAsync(() -> {
                System.out.println("1");
                return "1";
            }));
            add(CompletableFuture.supplyAsync(() -> {
                System.out.println("2");
                return "2";
            }));
            add(CompletableFuture.supplyAsync(() -> {
                System.out.println("3");
                return "3";
            }));
        }};
        CompletableFuture<Object> result = CompletableFuture.anyOf(futureList.toArray(new CompletableFuture[futureList.size()]));
        System.out.println(result.get());
    }

}
