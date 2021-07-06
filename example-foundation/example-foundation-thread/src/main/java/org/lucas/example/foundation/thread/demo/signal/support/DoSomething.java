package org.lucas.example.foundation.thread.demo.signal.support;

import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-20
 * @description:
 */
public class DoSomething implements Runnable {

    public DoSomething(String name) {
        Thread.currentThread().setName(name);
    }

    private static ConcurrencyThrottleSupport concurrencyThrottleSupportTest = new ConcurrencyThrottleSupport();

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
