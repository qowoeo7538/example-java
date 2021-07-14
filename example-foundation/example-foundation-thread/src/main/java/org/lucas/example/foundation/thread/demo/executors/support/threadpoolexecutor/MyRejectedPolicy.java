package org.lucas.example.foundation.thread.demo.executors.support.threadpoolexecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class MyRejectedPolicy implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r+"被拒绝执行"+System.currentTimeMillis());
    }

}