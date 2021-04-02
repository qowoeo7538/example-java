package org.lucas.example.foundation.thread.demo.future.support;

import java.util.concurrent.Callable;

/**
 * @create: 2017-11-13
 * @description:
 */
public class CalculateService implements Callable<Integer> {

    private Integer max;

    public CalculateService(Integer max) {
        this.max = max;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(2000);
        int results = 0;
        for (int i = 0; i <= max; i++) {
            results += i;
        }
        return results;
    }
}
