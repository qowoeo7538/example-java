package org.shaw.thread.executor.future;

import org.shaw.util.DataProducer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture不依赖线程池完成任务之后获取结果
 * CompletableFuture可以立即获得结果,不必等待整个线程完成任务
 */
public class CompletableFutureDemo {
    public static void main(String[] args) {
        try {
            CompletableFuture<String> completableFuture = test1();
            System.out.println(completableFuture.get());
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 异步任务模拟
     *
     * @return CompletableFuture 执行结果
     */
    public static CompletableFuture test1() {
        CompletableFuture<String> completableFuture = new CompletableFuture();
        new Thread(() -> {
            System.out.println("task doing...");
            try {
                // int i = 1 / 0; 当发生无法捕获的异常,get()方法会进行阻塞
                Thread.sleep(DataProducer.nextInt(1000, 5000));
                // 当获取到数据之后,主线程调用方法会立即收到信息
                completableFuture.complete("success");
                Thread.sleep(DataProducer.nextInt(1000, 5000));
                System.out.println("释放资源");
                Thread.sleep(DataProducer.nextInt(1000, 5000));
                System.out.println("释放完毕");
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        }).start();
        return completableFuture;
    }
}
