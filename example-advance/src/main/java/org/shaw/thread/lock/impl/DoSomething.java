package org.shaw.thread.lock.impl;

import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-20
 * @description:
 */
public class DoSomething implements Runnable {

    public DoSomething(String name) {
        Thread.currentThread().setName(name);
    }

    private static ConcurrencyThrottleSupportTest concurrencyThrottleSupportTest = new ConcurrencyThrottleSupportTest();

    @Override
    public void run() {
        try {
            concurrencyThrottleSupportTest.beforeAccess();
            System.out.println(Thread.currentThread().getName() + "任务运行中");
            TimeUnit.SECONDS.sleep(10);
            concurrencyThrottleSupportTest.afterAccess();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
