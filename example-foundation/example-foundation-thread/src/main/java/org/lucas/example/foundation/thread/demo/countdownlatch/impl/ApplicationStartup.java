package org.lucas.example.foundation.thread.demo.countdownlatch.impl;

import org.lucas.example.foundation.core.task.ExampleThreadExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @create: 2017-11-06
 * @description:
 */
public class ApplicationStartup {

    private static final CountDownLatch LATCH = new CountDownLatch(3);

    private static final List<BaseChecker> SERVICE = new ArrayList<>(4);

    public static boolean checkServices() throws InterruptedException {
        SERVICE.add(new NetworkChecker("一号线程", LATCH));
        SERVICE.add(new NetworkChecker("二号线程", LATCH));
        SERVICE.add(new NetworkChecker("三号线程", LATCH));
        for (final BaseChecker task : SERVICE) {
            // 每个线程任务执行完成之后通过调用 CountDownLatch.countDown() 将计数器-1。
            ExampleThreadExecutor.execute(task);
        }
        // 等待 LATCH 计数器变为0之后继续执行。
        LATCH.await();
        for (final BaseChecker v : SERVICE) {
            if (!v.isServiceUp()) {
                return false;
            }
        }
        return true;
    }
}
