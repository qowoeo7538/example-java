package org.shaw.util.thread;

import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * @create: 2017-11-17
 * @description:
 */
public class ScheduledThreadFactory {
    static {
        ExecutorConfigurationSupport executorConfigurationSupport = new ExecutorConfigurationSupport() {
            @Override
            protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
                return null;
            }
        };
    }
}
