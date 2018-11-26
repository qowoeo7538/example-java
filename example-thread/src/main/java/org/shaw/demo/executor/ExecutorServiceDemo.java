package org.shaw.demo.executor;

import org.shaw.demo.executor.impl.ServiceTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * @create: 2018-01-03
 * @description: 使用执行器批量线程运行并带有返回值
 */
public class ExecutorServiceDemo {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(new ServiceTask(1, 100));
        tasks.add(new ServiceTask(100, 10000));
        try {
            List<Future<Integer>> returnDatas = service.invokeAll(tasks);
            for (int i = 0; i < returnDatas.size(); i++) {
                System.out.println("第[" + i + "]个线程: " + returnDatas.get(i).get());
            }
            // 尝试按顺序关闭，不接受新的执行任务
            service.shutdown();
            // 设置超时时间
            service.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }
}
