package org.lucas.example.foundation.thread.demo.future;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.thread.demo.future.support.Shop;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ExceptionFutureActionDemo {

    @Test
    public void testAsync() {
        Shop shop = new Shop("Best Shop");
        long start = System.nanoTime();
        // 导致线程永久阻塞
        Future<Double> futurePrice = shop.getPriceAsyncException("mac book pro");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("异步方法调用花费时间: --- " + invocationTime + " --- msecs");

        //...其他操作
        doSomethingElse();

        //从future对象中读取价格,如果价格未知,则发生阻塞.
        try {
            Double price = futurePrice.get();
            System.out.printf(shop.getName() + " Price is %.2f%n", price);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("异步方法返回价格所需时间: --- " + retrievalTime + " ---msecs");

    }

    /**
     * 模拟操作
     */
    private static void doSomethingElse() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
