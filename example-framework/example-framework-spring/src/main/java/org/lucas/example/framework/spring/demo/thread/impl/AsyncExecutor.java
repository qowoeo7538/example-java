package org.lucas.example.framework.spring.demo.thread.impl;

import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.locks.LockSupport;

public class AsyncExecutor {

    /**
     * 线程池执行器
     */
    private TaskExecutor taskExecutor;

    private class MessagePrinterTask implements Runnable {

        private String message;

        private Thread monitor;

        public MessagePrinterTask(String message, Thread monitor) {
            this.message = message;
            this.monitor = monitor;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " " + message);
                LockSupport.unpark(monitor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void printMessages() {
        for (int i = 0; i < 6; i++) {
            taskExecutor.execute(new MessagePrinterTask("Message" + i, Thread.currentThread()));
            LockSupport.park(Thread.currentThread());
        }
    }


}
