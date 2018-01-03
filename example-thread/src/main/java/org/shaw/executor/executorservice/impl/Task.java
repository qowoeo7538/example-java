package org.shaw.executor.executorservice.impl;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @create: 2018-01-03
 * @description:
 */
public class Task implements Callable<Integer> {

    private int start;
    private int end;

    public Task(int start, int end) {
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