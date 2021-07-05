package org.lucas.example.foundation.thread.demo.lock.support;

public class ChangeLock {

    private String lock = "lock handler";

    public void method(){
        synchronized (lock) {
            try {
                System.out.println("当前线程 : "  + Thread.currentThread().getName() + "开始");
                //锁的引用被改变,则其他线程可获得锁，导致并发问题
                lock = "change lock handler";
                Thread.sleep(2000);
                System.out.println("当前线程 : "  + Thread.currentThread().getName() + "结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
