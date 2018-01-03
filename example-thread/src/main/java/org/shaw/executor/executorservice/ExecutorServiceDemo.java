package org.shaw.executor.executorservice;

import org.shaw.executor.executorservice.impl.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @create: 2018-01-03
 * @description: 使用执行器批量线程运行并带有返回值
 */
public class ExecutorServiceDemo {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
        tasks.add(new Task(1, 100));
        tasks.add(new Task(100, 10000));
        try {
            List<Future<Integer>> returnDatas = service.invokeAll(tasks);
            for (int i = 0; i < returnDatas.size(); i++) {
                System.out.println("第[" + i + "]个线程: " + returnDatas.get(i).get());
            }
            service.shutdown(); // 尝试按顺序关闭，不接受新的执行任务
            service.awaitTermination(30, TimeUnit.SECONDS); //设置超时时间
        } catch (InterruptedException | ExecutionException e) {
        }
    }
}
