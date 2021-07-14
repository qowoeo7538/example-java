package org.lucas.example.foundation.thread.demo.executors;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.thread.demo.executors.support.ScheduledTask;
import org.lucas.example.foundation.thread.demo.executors.support.ServiceTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @create: 2018-01-03
 * @description: 使用执行器批量线程运行并带有返回值
 */
public class ExecutorsDemo {

    /**
     * 固定线程数量线程池
     */
    @Test
    public void demoFixedThreadPool() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(new ServiceTask(1, 100));
        tasks.add(new ServiceTask(100, 10000));
        try {
            List<Future<Integer>> returnDatas = service.invokeAll(tasks);
            for (int i = 0; i < returnDatas.size(); i++) {
                System.out.println("第[" + i + "]个线程: " + returnDatas.get(i).get());
            }
            // 方法不阻塞, 调用后不再接收新的任务,所有线程池内任务执行完毕则关闭线程池
            service.shutdown();
            // 等待线程执行完成,设置超时时间
            service.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 底层还是通过 ThreadPoolExecutor 实现
     */
    @Test
    public void demoCachedThreadPool() throws InterruptedException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
			/*try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
            Runnable task = () -> {
                try {
                    Thread.sleep(index * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ">>" + index);
            };
            cachedThreadPool.execute(task);
        }
        cachedThreadPool.awaitTermination(30, TimeUnit.SECONDS);
    }

    /**
     * 调度线程池
     */
    @Test
    public void demoScheduledThreadPool() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < 5; i++) {
            service.schedule(new ScheduledTask(1, 100), i, TimeUnit.SECONDS);
        }
        service.shutdown();
        try {
            service.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void demoSingleThreadExecutor() throws Exception {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Runnable task = () -> {
                try {
                    System.out.println(Thread.currentThread().getName() + ">>" + index);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            singleThreadExecutor.execute(task);
        }
        singleThreadExecutor.shutdown();
        singleThreadExecutor.awaitTermination(100, TimeUnit.SECONDS);
    }

}
