package org.lucas.example.foundation.thread.demo.signal.support;

import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-20
 * @description:
 */
public class ConcurrencyThrottleSupportTest implements Runnable {

    private final static String THREAD_NAME = "Thread-0";

    public ConcurrencyThrottleSupportTest(String name) {
        Thread.currentThread().setName(name);
    }

    private static ConcurrencyThrottleSupport concurrencyThrottleSupportTest = new ConcurrencyThrottleSupport();

    @Override
    public void run() {
        try {
            concurrencyThrottleSupportTest.beforeAccess();
            System.out.println(Thread.currentThread().getName() + "任务运行中");
            if (THREAD_NAME.equals(Thread.currentThread().getName())) {
                TimeUnit.SECONDS.sleep(10);
            } else {
                TimeUnit.SECONDS.sleep(5);
            }
            concurrencyThrottleSupportTest.afterAccessNotifyAll();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
