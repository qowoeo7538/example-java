package org.lucas.example.foundation.thread.demo.signal.support;

import org.lucas.example.foundation.core.util.DataProducerHelper;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @create: 2017-11-06
 * @description:
 */
public class NetworkChecker extends BaseChecker {

    public NetworkChecker(String name, CountDownLatch latch) {
        super(name, latch);
    }

    @Override
    public void verifyService() {
        System.out.println(getserviceName() + "时间:" + new Date() + "准备！");
        try {
            Thread.sleep(DataProducerHelper.nextInt(1000, 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getserviceName() + "时间:" + new Date() + "完成！");
    }
}
