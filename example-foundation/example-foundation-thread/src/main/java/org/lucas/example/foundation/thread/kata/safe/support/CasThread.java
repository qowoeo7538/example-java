package org.lucas.example.foundation.thread.kata.safe.support;

import java.util.concurrent.atomic.AtomicInteger;

public class CasThread implements Runnable {

    private final AtomicInteger count;

    public CasThread(final AtomicInteger count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            safeCount();
        }
    }

    /**
     * 使用CAS实现线程安全计数器
     */
    private void safeCount() {
        for (; ; ) {
            int i = count.get();
            // 如果当前值等于期望值，
            boolean suc = count.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

}
