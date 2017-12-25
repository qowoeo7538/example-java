package org.shaw.nio.thread.concurrent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 执行器
 * newFixedThreadPool(int nThreads) :设置的最大线程数量是x，而提交的线程数y，那么（y-x）对应的这些线程要等到前x个线程执行完毕才会执行。
 */
public class ExecutorTest {
    public static void main(String[] args) {
        scheduledTest();
    }

    /**
     * 延时线程任务
     */
    public static void scheduledTest() {
        ScheduledThreadPoolExecutor service = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        for (int i = 0; i < 5; i++) {
            service.schedule(new Task(1, 100), i, TimeUnit.SECONDS);
        }
        service.shutdown();
        try {
            service.awaitTermination(30, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
    }

    /**
     * 使用执行器批量线程运行并带有返回值
     */
    public static void executorTest() {
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

    public static class Task implements Callable<Integer> {
        private int start;
        private int end;

        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(new Date());
            int returnData = 0;
            for (int i = start; i < end; i++) {
                returnData += i;
            }
            return returnData;
        }
    }
}

class ExecutorThreadPool {
    public static ExecutorService executor = null;

    public static void execute(Runnable runnable) {
        if (executor == null) {
            executor = Executors.newCachedThreadPool();
        }
        executor.execute(runnable);
    }
}
