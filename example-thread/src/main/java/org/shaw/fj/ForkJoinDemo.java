package org.shaw.fj;

import org.junit.Test;
import org.shaw.fj.impl.ComputeTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * ForkJoinTask<V>:描述任务的抽象类
 * ForkJoinPool:管理ForkJoinTask的线程池
 * RecursiveAction:ForkJoinTask子类，描述无返回值的任务;
 * RecursiveTask<V>:ForkJoinTask子类，描述有返回值的任务;
 * <p>
 * 类似执行器 Executor,比执行器多了对任务更小单元的拆分功能,将任务进行分片并行处理.
 **/
public class ForkJoinDemo {

    @Test
    public void forkJoinTest() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ComputeTask task = new ComputeTask(0, 1000001);
        Future<Long> result = forkJoinPool.submit(task);
        try {
            if (task.isCompletedAbnormally()) {
                // 如果任务抛出异常或取消
                System.out.println(task.getException());
            }
            System.out.println("result: " + result.get());
            forkJoinPool.shutdown();
            forkJoinPool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

}
