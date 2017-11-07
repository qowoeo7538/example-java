package org.shaw.advance.thread.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @create: 2017-11-06
 * @description:
 */
public class NetworkHealthChecker extends BaseHealthChecker {

    public NetworkHealthChecker(String name, CountDownLatch latch) {
        super(name, latch);
    }

    @Override
    public void verifyService() {
        System.out.println(getserviceName() + "准备！");
        try {
            Thread.sleep((int) Math.random() * 10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getserviceName() + "完成！");
    }
}
