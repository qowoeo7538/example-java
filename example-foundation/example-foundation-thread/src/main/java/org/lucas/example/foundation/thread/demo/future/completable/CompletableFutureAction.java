package org.lucas.example.foundation.thread.demo.future.completable;

import org.junit.jupiter.api.Test;
import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.lucas.example.foundation.thread.demo.future.completable.impl.Discount;
import org.lucas.example.foundation.thread.demo.future.completable.impl.Quote;
import org.lucas.example.foundation.thread.demo.future.completable.impl.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;

public class CompletableFutureAction {

    /**
     * 异步计算
     */
    @Test
    public void testAsync() {
        Shop shop = new Shop("Best Shop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("mac book pro");
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
     * 使用并行流对请求进行并行操作
     */
    @Test
    public void testParallelStream() {
        long start = System.nanoTime();
        String product = "iphone666s";
        List<Shop> shops = Arrays.asList(new Shop("淘宝"),
                new Shop("天猫"),
                new Shop("京东"),
                new Shop("亚马逊"));
        List<String> list = shops.parallelStream()
                .map(shop ->
                        String.format("%s price is %.2f RMB",
                                shop.getName(),
                                shop.getPrice(product)))

                .collect(toList());

        System.out.println(list);
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " ms");
    }

    /**
     * 使用 CompletableFuture 发起异步请求
     */
    @Test
    public void testFuture() {
        long start = System.nanoTime();
        String product = "iphone666s";
        List<Shop> shops = Arrays.asList(new Shop("淘宝"),
                new Shop("天猫"),
                new Shop("京东"),
                new Shop("亚马逊"));
        List<CompletableFuture<String>> futures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f RMB",
                                shop.getName(),
                                shop.getPrice(product)))
                )
                .collect(toList());
        List<String> list = futures.stream()
                .map(CompletableFuture::join)
                .collect(toList());

        System.out.println(list);
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " ms");
    }

    /**
     * 得到折扣商店信息(已经被解析过)
     */
    @Test
    public void testDiscountPrice() {
        long start = System.nanoTime();
        String product = "iphone666s";
        List<Shop> shops = Arrays.asList(new Shop("淘宝"),
                new Shop("天猫"),
                new Shop("京东"),
                new Shop("亚马逊"));
        List<CompletableFuture<String>> futureList = shops.stream()
                .map(discountShop -> CompletableFuture.supplyAsync(
                        //异步方式取得商店中产品价格
                        () -> discountShop.getDiscountPrice(product), new ThreadPoolTaskExecutor()))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(
                        quote -> CompletableFuture.supplyAsync(
                                //使用另一个异步任务访问折扣服务
                                () -> Discount.applyDiscount(quote), new ThreadPoolTaskExecutor())))
                .collect(toList());

        //等待流中所有future执行完毕,并提取各自的返回值.
        List<String> list = futureList.stream()
                //join想但与future中的get方法,只是不会抛出异常
                .map(CompletableFuture::join)
                .collect(toList());

        System.out.println(list);
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " ms");
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
