package org.shaw.thread.lock.impl;

import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-20
 * @description:
 */
public class NotifyAllTest implements Runnable {

    private final static String THREAD_NAME = "Thread-0";

    public NotifyAllTest(String name) {
        Thread.currentThread().setName(name);
    }

    private static ConcurrencyThrottleSupportTest concurrencyThrottleSupportTest = new ConcurrencyThrottleSupportTest();

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
