package org.shaw.base.thread;

import org.shaw.base.thread.impl.MainThread;

import java.util.Scanner;

/**
 * @create: 2018-01-23
 * @description:
 */
public class DaemonThreadDemo {
    public static void main(String[] args) {
        MainThread mainThread = new MainThread("/home/joy/桌面/a.properties");
        mainThread.start();
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (!str.equals("") && str.length() > 0){
            mainThread.keepRunning = false;
        }
    }
}
