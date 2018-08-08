package org.shaw.util;

import org.shaw.core.task.StandardThreadExecutor;

public class ThreadTestUtils {

    /**
     * 等待子线程运行完成.
     * 否则 junit 主线程完成将会完成，导致无法捕获子线程异常。
     *
     * @param threads 线程组
     */
    public static void test(Thread... threads) {
        for (Thread thread : threads) {
            thread.start();
        }

        while (isAlive(threads)) {
            Thread.yield();
        }

    }

    /**
     * 至少一个线程存活
     *
     * @param threads 线程组
     * @return 如果一个线程存活返回 true.
     */
    private static boolean isAlive(Thread... threads) {
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                return true;
            }
        }
        return false;
    }
}
