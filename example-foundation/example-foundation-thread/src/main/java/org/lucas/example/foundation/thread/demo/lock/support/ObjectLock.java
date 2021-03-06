package org.lucas.example.foundation.thread.demo.lock.support;

/**
 * 对象锁
 */
public class ObjectLock extends Thread {

    private int count = 0;

    /**
     *  synchronized 是获得对象锁,如果作用在static类型上,则升级为类锁
     */
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
