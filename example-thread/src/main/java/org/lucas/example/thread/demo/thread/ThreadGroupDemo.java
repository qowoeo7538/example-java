package org.lucas.example.thread.demo.thread;

import org.lucas.example.thread.demo.thread.impl.MyThread;

/**
 * 线程组
 * <p>
 * 每个线程都会属于一个线程组
 * <p>
 * 作用:可以批量管理线程或线程组对象，有效地对线程或线程组对象进行组织。
 */
public class ThreadGroupDemo {
    public static void main(String[] args) {
        /**
         * 获取主线程所在的线程组，这是所有线程默认的线程组
         */
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        System.out.println("主线程组的名字：" + mainGroup.getName());
        System.out.println("主线程组是否是后台线程组：" + mainGroup.isDaemon());
        new MyThread("主线程组的线程").start();

        /**
         * 自定义线程组
         */
        ThreadGroup tg = new ThreadGroup("新线程组");
        tg.setDaemon(true);
        System.out.println("tg线程组是否是后台线程组：" + tg.isDaemon());
        MyThread tt = new MyThread(tg, "tg组的线程甲");
        tt.start();
        new MyThread(tg, "tg组的线程乙").start();
    }
}
