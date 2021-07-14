package org.lucas.example.foundation.thread.demo.executors.support.threadpoolexecutor;

public class MyTask implements Runnable {

    int index = 0;

    public MyTask(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ">>run " + index);
    }

}

