package org.shaw.concurrent.threadfactory.impl;

import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-08
 * @description:
 */
public class Task implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
