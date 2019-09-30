package org.lucas.example.foundation.thread.demo.executor.impl;

import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-08
 * @description:
 */
public class FactoryTask implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
