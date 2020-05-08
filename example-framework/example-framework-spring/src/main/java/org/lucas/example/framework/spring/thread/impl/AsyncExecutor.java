package org.lucas.example.framework.spring.thread.impl;

import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.locks.LockSupport;

public class AsyncExecutor {

    /**
     * 线程池执行器
     */
    private TaskExecutor taskExecutor;




    private class MessagePrinterTask implements Runnable {

        private String message;

        public MessagePrinterTask(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " " + message);
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
            taskExecutor.execute(new MessagePrinterTask("Message" + i));
        }
    }


}
