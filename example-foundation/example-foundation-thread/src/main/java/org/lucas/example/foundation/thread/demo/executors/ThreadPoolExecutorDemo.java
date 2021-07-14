package org.lucas.example.foundation.thread.demo.executors;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.thread.demo.executors.support.threadpoolexecutor.MyRejectedPolicy;
import org.lucas.example.foundation.thread.demo.executors.support.threadpoolexecutor.MyTask;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {

    /**
     * AbortPolicy			抛出异常,不影响其他线程运行
     * CallerRunsPolicy		调用当前任务
     * DiscardOldestPolicy	丢弃最老的任务
     * DiscardPolicy	    直接丢弃,什么也不做
     */
    @Test
    public void demoRejectedPolicy() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 0,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), new MyRejectedPolicy());
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(7,7,60L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 8; i++) {
            MyTask task = new MyTask(i);
            executor.submit(task);
        }

        executor.shutdown();
    }

}
