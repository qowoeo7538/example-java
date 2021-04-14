package org.lucas.example.foundation.thread.demo.thread.support;

/**
 * @create: 2017-11-08
 * @description:
 */
public class MyThread extends Thread {
    /**
     * 提供指定线程名
     *
     * @param name 线程名
     */
    public MyThread(String name) {
        super(name);
        System.out.println("constructor-----线程名称：" + getName() + ",状态：" + getState());
        System.out.println("开始");
        // yield()可能会让出本次cpu的执行时间片段,不建议使用该方法,因为该方法会导致cpu产生频繁的线程调度,从而导致CPU的使用率飙高.
        yield();
        System.out.println("结束");
    }

    /**
     * 提供指定线程名、线程组的构造器
     *
     * @param group 线程组的构造器
     * @param name  线程名
     */
    public MyThread(ThreadGroup group, String name) {
        super(group, name);
    }

    @Override
    public void run() {
        System.out.println("run-----线程名称：" + getName() + ",状态：" + getState());
        for (int i = 0; i < 20; i++) {
            System.out.println(getName() + " 线程的i变量" + i);
        }
    }
}
