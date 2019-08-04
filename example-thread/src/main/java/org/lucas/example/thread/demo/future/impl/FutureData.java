package org.lucas.example.thread.demo.future.impl;

/**
 * @create: 2017-11-13
 * @description:
 */
public class FutureData implements Data {

    /**
     * 具体任务对象
     */
    private RealData realData;

    /**
     * 是否就绪
     */
    private boolean isReady = false;

    /**
     * 结果回调
     *
     * @return 结果
     */
    @Override
    public synchronized String getRequest() {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.realData.getRequest();
    }

    /**
     * 设置具体任务
     *
     * @param realData 任务对象
     */
    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notify();
    }

}
