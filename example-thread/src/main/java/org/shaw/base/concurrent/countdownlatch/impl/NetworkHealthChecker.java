package org.shaw.base.concurrent.countdownlatch.impl;

import org.shaw.util.DataProducer;

import java.util.Date;
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
        System.out.println(getserviceName() + "时间:" + new Date() + "准备！");
        try {
            Thread.sleep(DataProducer.nextInt(1000, 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getserviceName() + "时间:" + new Date() + "完成！");
    }
}
