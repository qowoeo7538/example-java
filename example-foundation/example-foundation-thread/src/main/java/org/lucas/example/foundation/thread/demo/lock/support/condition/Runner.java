package org.lucas.example.foundation.thread.demo.lock.support.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Runner extends Thread {

    private final ReentrantLock rl;

    private final Condition condition;

    public Runner(ReentrantLock rl, Condition condition) {
        this.rl = rl;
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            rl.lock();
            System.out.println(Thread.currentThread().getName() + " before await..");
            //condition.signal();
            condition.await();
            System.out.println(Thread.currentThread().getName() + " after await..");
            rl.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
