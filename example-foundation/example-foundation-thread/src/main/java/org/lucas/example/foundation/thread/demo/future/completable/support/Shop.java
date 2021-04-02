package org.lucas.example.foundation.thread.demo.future.completable.support;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

    /**
     * 商店名称
     */
    private String name;

    private Random random = new SecureRandom();


    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * (阻塞式)通过名称查询价格
     *
     * @param product
     * @return
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * (阻塞式)通过名称查询价格
     *
     * @param product
     * @return 返回  Shop-Name:price:DiscountCode 的格式字符串
     */
    public String getDiscountPrice(String product) {

        double price = calculatePrice(product);
        //随机得到一个折扣码
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            double price = calculatePrice(product);
            //需要长时间计算的任务结束并返回结果时,设置Future返回值
            future.complete(price);
        }).start();

        //无需等待还没结束的计算,直接返回Future对象
        return future;
    }

    public Future<Double> getPriceAsyncException(String product) {
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            double price = calculatePriceException(product);
            //需要长时间计算的任务结束并返回结果时,设置Future返回值
            future.complete(price);
        }).start();

        //无需等待还没结束的计算,直接返回Future对象
        return future;
    }

    /**
     * 计算价格(模拟一个产生价格的方法)
     *
     * @param product
     * @return
     */
    private double calculatePrice(String product) {
        delay();
        //数字*字符=数字(产生价格的方法)
        return random.nextDouble() * product.charAt(0) * product.charAt(1);
    }

    private double calculatePriceException(String product) {
        delay();
        //故意抛出 java.lang.ArithmeticException: / by zero 异常
        int i = 1 / 0;
        //数字*字符=数字(产生价格的方法)
        return 10 * product.charAt(0);
    }

    /**
     * 模拟耗时操作,阻塞1秒
     */
    private void delay() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
