package org.lucas.demo.countdownlatch.impl;

import java.util.concurrent.CountDownLatch;

/**
 * @create: 2017-11-06
 * @description:
 */
public abstract class BaseChecker implements Runnable {

    private boolean serviceUp;

    private final CountDownLatch latch;

    private final String serviceName;

    public BaseChecker(String serviceName, CountDownLatch latch) {
        this.latch = latch;
        this.serviceName = serviceName;
        this.serviceUp = false;
    }

    @Override
    public void run() {
        try {
            verifyService();
            serviceUp = true;
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            serviceUp = false;
        } finally {
            if (latch != null) {
                // 任务执行完成将计数器-1。
                latch.countDown();
            }
        }
    }

    public boolean isServiceUp() {
        return serviceUp;
    }

    public String getserviceName() {
        return this.serviceName;
    }

    public abstract void verifyService();
}
