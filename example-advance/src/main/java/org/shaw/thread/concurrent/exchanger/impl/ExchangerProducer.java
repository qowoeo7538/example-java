package org.shaw.thread.concurrent.exchanger.impl;

import org.shaw.base.thread.SecurityTask;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-16
 * @description:
 */
public class ExchangerProducer extends SecurityTask {

    private volatile boolean isDone;

    private Exchanger<Integer> exchanger;

    private static int data = 1;

    public ExchangerProducer(Exchanger<Integer> exchanger, Boolean isDone) {
        this.isDone = isDone;
        this.exchanger = exchanger;
    }

    @Override
    protected void runTask() {
        while (!Thread.interrupted() && !isDone) {
            for (int i = 1; i <= 3; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    data = i;
                    System.out.println("producer change before: " + data);
                    data = exchanger.exchange(data);
                    System.out.println("producer change after: " + data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            isDone = true;
        }
    }
}
