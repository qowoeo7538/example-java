package org.shaw.thread.future.impl;

import java.util.concurrent.Callable;

/**
 * @create: 2017-11-13
 * @description:
 */
public class RealService implements Callable<String> {

    private String Data;

    public RealService(String Data) {
        this.Data = Data;
    }

    @Override
    public String call() throws Exception {
        //利用sleep来表示任务处理
        Thread.sleep(2000);
        return "这是处理" + Data + "结果";
    }
}
