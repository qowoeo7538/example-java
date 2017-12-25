package org.shaw.base.concurrent;

import java.util.concurrent.*;

/**
 * ForkJoinTask<V>:描述任务的抽象类
 * ForkJoinPool:管理ForkJoinTask的线程池
 * RecursiveAction:ForkJoinTask子类，描述无返回值的任务;
 * RecursiveTask<V>:ForkJoinTask子类，描述有返回值的任务;
 *
 * 学习心得：类似执行器 Executor,比执行器多了对任务更小单元的拆分功能
 **/
public class ForkJoinTest {
    public static void main(String[] args) {
        fjDemo();
    }

    public static void fjDemo() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        Future<Long> result = forkJoinPool.submit(new MTask(0, 1000001));
        try {
            System.out.println("result: " + result.get());

            forkJoinPool.shutdown();

            forkJoinPool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
        }
    }

    public static class MTask extends RecursiveTask<Long> {

        static final int THRESHOLD = 1000; //临界值

        private int begin, end;

        public MTask(int begin, int end) {
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
                MTask left = new MTask(begin, mind);
                MTask right = new MTask(mind + 1, end);
                left.fork(); //运行分支线程
                right.fork();
                Long lr = left.join(); //获取返回值
                Long rr = right.join();
                returnDatas = lr + rr;
            }
            return returnDatas;
        }
    }
}
