package org.shaw.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by joy on 17-2-17.
 * 信号量:维护了一个许可集,通常用于限制可以访问某些资源（物理或逻辑的）的线程数目。
 */
public class SeDemo {
    public static void main(String[] args) {
        TestSemaphore();
    }

    public static void TestSemaphore(){
        ExecutorService executor = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 20; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("["+Thread.currentThread().getId()+"]等待获取许可！");
                        semaphore.acquire();
                        System.out.println("["+Thread.currentThread().getId()+"]已经获取许可！");
                        Thread.sleep((long) (Math.random() * 10000));
                        semaphore.release();
                        System.out.println("["+Thread.currentThread().getId()+"]完成业务！");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
        executor.shutdown();
    }
}

