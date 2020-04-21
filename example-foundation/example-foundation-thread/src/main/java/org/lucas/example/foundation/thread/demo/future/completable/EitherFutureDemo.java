package org.lucas.example.foundation.thread.demo.future.completable;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.util.DataProducerHelper;

import java.util.concurrent.CompletableFuture;

/**
 * applyToEither()：当任意一个 CompletionStage 完成的时候，fn会被执行，它的返回值会当作新的 CompletableFuture 的计算结果
 */
public class EitherFutureDemo {

    @Test
    public void demoApplyEither() {
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

}
