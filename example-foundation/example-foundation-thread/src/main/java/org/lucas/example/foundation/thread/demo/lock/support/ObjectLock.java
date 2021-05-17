package org.lucas.example.foundation.thread.demo.lock.support;

/**
 * 对象锁
 */
public class ObjectLock {

    private int count = 0;

    public synchronized void add() {
        count++;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ">count=" + count);
    }

}
