package org.lucas.example.foundation.thread.demo.executor.impl;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @create: 2018-01-03
 * @description:
 */
public class ScheduledTask implements Callable<Integer> {

    private int start;
    private int end;

    public ScheduledTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() {
        System.out.println(new Date());
        int returnData = 0;
        for (int i = start; i < end; i++) {
            returnData += i;
        }
        return returnData;
    }
}