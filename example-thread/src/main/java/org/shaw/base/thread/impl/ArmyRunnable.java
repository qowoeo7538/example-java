package org.shaw.base.thread.impl;

/**
 * @create: 2018-01-23
 * @description:
 */
public class ArmyRunnable implements Runnable {

    /**
     * 通过该属性控制任务是否终止
     *
     * volatile：每次写这个变量时会将该变量工作内存的值写入主内存中。读取时，会将主内存中的变量读取到工作内存中
     * 1）当结果依赖当前的值，如 keepRunning++ ，keepRunning+=5，不能使用。
     * 2）该变量在不变式中有其它volatile变量不能使用。
     */
    public volatile boolean keepRunning = true;

    @Override
    public void run() {
        while (keepRunning) {
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "进攻对方[" + i + "]次");
                //暂停当前正在执行的线程对象，并随机执行线程
                Thread.yield();
            }
        }
        //可以当作线程结束后的清理工作
        System.out.println(Thread.currentThread().getName() + "结束了战斗");
    }
}
