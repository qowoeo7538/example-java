package org.shaw.future;

import org.junit.Test;
import org.shaw.core.task.ExampleThreadExecutor;
import org.shaw.util.DataProducerHelper;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture不依赖线程池完成任务之后获取结果
 * CompletableFuture可以立即获得结果,不必等待整个线程完成任务
 */
public class CompletableFutureDemo {

    @Test
    public void testCompletableFuture() throws Exception {
        CompletableFuture<String> completableFuture = new CompletableFuture();
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
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });
        System.out.println(completableFuture.get());

        ExampleThreadExecutor.destroy();
    }
}
