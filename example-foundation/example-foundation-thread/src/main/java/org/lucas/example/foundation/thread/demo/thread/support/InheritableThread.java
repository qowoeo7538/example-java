package org.lucas.example.foundation.thread.demo.thread.support;

public class InheritableThread extends Thread {

    /**
     * 多个线程之间读取副本, 父子线程之间复制传递
     */
    public static InheritableThreadLocal<Integer> tl;

    public InheritableThread() {
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " get tl is : " + tl.get());
        new InheritableThread().start();
    }
}
