package org.lucas.example.thread.demo.future;

import org.junit.jupiter.api.Test;
import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.core.util.DataProducerHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * get(): 阻塞获取结果，实现Future的get接口，显式抛出异常.
 * getNow(T valueIfAbsent): 立即获取执行结果，如果当前任务未执行完成，则返回valueIfAbsent.
 * join(): 执行完成后返回执行结果，或者抛出unchecked异常.
 * get(long timeout, TimeUnit unit): 在有限时间内获取数据
 */
public class CompletableFutureDemo {

    /**
     * complete
     * <p>
     * 可以使 CompletableFuture 立即获得结果,不必等待整个线程完成任务。
     */
    @Test
    public void testComplete() throws Exception {
        final CompletableFuture<String> completableFuture = new CompletableFuture<>();

        ExampleThreadExecutor.submit(() -> {
            System.out.println("task doing...");
            try {
                // int i = 1 / 0; 当发生无法捕获的异常,get()方法会进行阻塞
                Thread.sleep(DataProducerHelper.nextInt(1000, 5000));
                // 当获取到数据之后,主线程调用方法会立即收到信息
                completableFuture.complete("success");
                Thread.sleep(DataProducerHelper.nextInt(1000, 5000));
                System.out.println("释放资源");
                Thread.sleep(DataProducerHelper.nextInt(1000, 5000));
                System.out.println("释放完毕");
            } catch (final Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });
        System.out.println(completableFuture.get());

        ExampleThreadExecutor.destroy();
    }

    /**
     * supplyAsync
     * <p>
     * 有返回值任务
     */
    @Test
    public void testSupplyAsync() {
        // 设置日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
                        dateFormat.format(new Date()),
                new ThreadPoolTaskExecutor()
        ).thenApply(result ->
                "当前时间: " + result
        ).handleAsync((v, e) ->
                // 当原先的 CompletableFuture 的值计算完成或者抛出异常的时候，会触发这个对象的计算
                "value is = " + v + " && exception is =  " + e
        );
        System.out.println(future.join());
    }

    /**
     * runAsync
     * <p>
     * 无返回值任务
     */
    @Test
    public void testRunAsync() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CompletableFuture.runAsync(() -> {
            System.out.println("sleep for 1s :" + dateFormat.format(new Date()));
            throw new IllegalArgumentException("illegal exception!");
        }).exceptionally((e) -> {
            // 当原始的 CompletableFuture 抛出异常的时候，就会触发这个对象的计算。
            System.out.println(e.getMessage());
            return null;
        }).whenComplete((t, u) ->
                System.out.println("complete")
        )// 在完成时返回结果值，或在异常时抛出(未检查的)异常。
                .join();
    }

    /**
     * 返回参数转换
     */
    @Test
    public void testConvert() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        String result = future.thenApplyAsync(i -> i * 10)
                // 将上个 CompletableFuture 的结果作为新的 CompletableFuture 对象，来完成参数类型的转换。
                // thenApply 无法处理异常
                .thenApply(i -> i.toString())
                .join();
        System.out.println(result);
    }

    // ===================== 任务注册 =============================

    /**
     * thenAccept
     * <p>
     * 会在前一个操作完成后根据结果继续执行，但无返回值。
     */
    @Test
    public void testAccept() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        }).thenAccept(System.out::println).join();
    }

    /**
     * thenRun
     * <p>
     * 与 thenAccept 不同，并不使用前一个操作的计算的结果进行下次执行。
     */
    @Test
    public void testRun() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("执行CompletableFuture");
            return "first";
        }).thenRun(() ->
                System.out.println("finished")
        ).join();
    }

    // ===================== 结果合并 =============================

    /**
     * thenAcceptBoth
     * <p>
     * 将两个操作的计算结果合并运行一次。
     */
    @Test
    public void testAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "first";
        }).thenAcceptBoth(CompletableFuture.completedFuture("second"),
                (first, second) -> System.out.println(first + " : " + second)
        ).join();
    }

    // ===================== 操作合并 =============================

    /**
     * thenCompose
     * <p>
     * 第一个操作完成时，将其结果作为参数传递给第二个操作继续运行。
     * <p>
     * thenCompose 返回的对象并不一是函数fn返回的对象，
     * 如果原来的CompletableFuture还没有计算出来，它就会生成一个新的组合后的CompletableFuture。
     */
    @Test
    public void testCompose() {
        int f = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "first";
        }).thenCompose(str -> CompletableFuture.supplyAsync(() -> {
            String str2 = "second";
            return str.length() + str2.length();
        })).join();
        System.out.println("字符串长度为：" + f);
    }

    /**
     * thenCombineAsync
     * <p>
     * 将两个 CompletableFuture 对象的执行结果整合起来，并且执行是并行的。
     */
    @Test
    public void testCombine() {
        String f = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "first";
        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> "second"), (x, y) -> y + ":" + x)
                .join();
        System.out.println(f);
    }

    // ===================== 条件执行 =============================

    /**
     * applyToEither
     * <p>
     * 当任意一个 CompletionStage 完成的时候，fn会被执行，它的返回值会当作新的 CompletableFuture<U> 的计算结果
     */
    @Test
    public void testApplyEither() {
        String f = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100 + DataProducerHelper.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "first";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100 + DataProducerHelper.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "second";
        }), i -> "result: " + i).join();
        System.out.println(f);
    }

    /**
     * allOf方法是当所有的CompletableFuture都执行完后执行计算。
     * anyOf方法是当任意一个CompletableFuture执行完后就会执行计算，计算的结果相同。
     */
    @Test
    public void testAnyAll() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100 + DataProducerHelper.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "first";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100 + DataProducerHelper.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "second";
        });
        // CompletableFuture.allOf(future1, future2).join();
        Object cf = CompletableFuture.anyOf(future1, future2).join();
        System.out.println(cf);
    }

}
