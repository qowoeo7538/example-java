package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.thread.demo.thread.support.MainThread;

import java.util.Scanner;

/**
 * @create: 2018-01-23
 * @description:
 */
public class DaemonThreadDemo {

    @Test
    void demoDaemonThread1() {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("= =!");
            }
        });

        // 打开和关闭这个设置观察JVM进程是否终止
        thread.setDaemon(true);
        thread.start();

        //输出线程是否为守护线程
        System.out.println(thread.getName() + " is daemon? " + thread.isDaemon());
        System.out.println(Thread.currentThread().getName() + " is daemon? " + Thread.currentThread().isDaemon());

        System.out.println("main is over");
    }

    @Test
    void demoDaemonThread2() {
        MainThread mainThread = new MainThread("/home/joy/桌面/a.properties");
        mainThread.start();
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (!str.equals("") && str.length() > 0){
            mainThread.keepRunning = false;
        }
    }

}
