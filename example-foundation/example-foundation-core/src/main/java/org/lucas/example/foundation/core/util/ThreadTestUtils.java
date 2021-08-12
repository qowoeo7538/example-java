package org.lucas.example.foundation.core.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThreadTestUtils {

    /**
     * 等待子线程运行完成.
     * 否则 junit 主线程完成将会完成，导致无法捕获子线程异常。
     */
    public static void complete(final Thread... threads) {
        for (Thread thread : threads) {
            while (thread.isAlive()) {
                Thread.yield();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 等待子线程运行完成.
     * 否则 junit 主线程完成将会完成，导致无法捕获子线程异常。
     */
    public static void complete(final List<Thread> threads) {
        for (Thread thread : threads) {
            while (thread.isAlive()) {
                Thread.yield();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
