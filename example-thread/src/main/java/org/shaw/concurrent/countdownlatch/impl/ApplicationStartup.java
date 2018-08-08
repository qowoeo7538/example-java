package org.shaw.concurrent.countdownlatch.impl;

import org.shaw.core.task.ExampleThreadExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @create: 2017-11-06
 * @description:
 */
public class ApplicationStartup {

    private static CountDownLatch latch;

    private static List<BaseHealthChecker> service;

    public static boolean checkExternalServices() throws InterruptedException {
        latch = new CountDownLatch(3);
        service = new ArrayList(3);
        service.add(new NetworkHealthChecker("一号线程", latch));
        service.add(new NetworkHealthChecker("二号线程", latch));
        service.add(new NetworkHealthChecker("三号线程", latch));
        for (final BaseHealthChecker task : service) {
            ExampleThreadExecutor.execute(task);
        }
        latch.await();
        ExampleThreadExecutor.destroy();
        for (final BaseHealthChecker v : service) {
            if (!v.isServiceUp()) {
                return false;
            }
        }
        return true;
    }
}
