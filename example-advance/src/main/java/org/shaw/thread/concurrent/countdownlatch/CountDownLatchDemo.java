package org.shaw.thread.concurrent.countdownlatch;

/**
 * Created by joy on 17-2-17.
 * CountDownLatch:完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        boolean result = false;
        try {
            result = ApplicationStartupUtil.checkExternalServices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("外部业务验证完成，结果是：" + result);
    }
}