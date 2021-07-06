package org.lucas.example.foundation.thread.demo.signal;


import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.thread.demo.signal.support.ApplicationStartup;

/**
 * 完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 */
public class CountDownLatchDemo {

    @Test
    public void countDownLatchTest(){
        boolean result = false;
        try {
            result = ApplicationStartup.checkServices();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        System.out.println("外部业务验证完成，结果是：" + result);
    }

}
