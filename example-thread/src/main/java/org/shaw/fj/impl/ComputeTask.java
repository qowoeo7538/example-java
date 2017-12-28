package org.shaw.fj.impl;

import java.util.concurrent.RecursiveTask;

/**
 * @create: 2017-12-28
 * @description:
 */
public class ComputeTask extends RecursiveTask<Long> {

    /** 临界值 */
    static final int THRESHOLD = 1000;

    private int begin, end;

    public ComputeTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long returnDatas = 0;
        if (end - begin <= THRESHOLD) {
            for (int i = begin; i < end; i++) {
                returnDatas += i;
            }
        } else {
            int mind = (begin + end) / 2;
            ComputeTask left = new ComputeTask(begin, mind);
            ComputeTask right = new ComputeTask(mind + 1, end);
            //运行分支线程
            left.fork();
            right.fork();
            //获取返回值
            Long lr = left.join();
            Long rr = right.join();
            returnDatas = lr + rr;
        }
        return returnDatas;
    }
}
