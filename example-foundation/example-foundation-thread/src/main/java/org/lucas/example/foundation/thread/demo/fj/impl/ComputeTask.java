package org.lucas.example.foundation.thread.demo.fj.impl;

import java.util.concurrent.RecursiveTask;

/**
 * @create: 2017-12-28
 * @description:
 */
public class ComputeTask extends RecursiveTask<Long> {

    /** 任务大小阈值 */
    private static final int THRESHOLD = 1000;

    /** 任务大小区间 */
    private int begin, end;

    public ComputeTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long returnDatas = 0;
        // 判断任务是否超过阀值
        if (end - begin <= THRESHOLD) {
            // 小于则进行计算
            for (int i = begin; i < end; i++) {
                returnDatas += i;
            }
        } else {
            // 如果任务大于阀值，就分裂成两个子任务计算
            int mind = (begin + end) / 2;
            ComputeTask left = new ComputeTask(begin, mind);
            ComputeTask right = new ComputeTask(mind + 1, end);
            // 运行分支线程
            left.fork();
            right.fork();
            // 获取返回值
            Long lr = left.join();
            Long rr = right.join();
            // 合并返回值
            returnDatas = lr + rr;
        }
        return returnDatas;
    }
}
