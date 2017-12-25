package org.shaw.nio.thread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by joy on 17-2-16.
 * 模拟守护线程，当用户线程结束，守护线程随着结束
 */
public class DaemonThread implements Runnable {
    String fileName;

    public DaemonThread(String fileName){
        this.fileName = fileName;
    }

    public void run() {
        System.out.println("进入守护线程"+Thread.currentThread().getId());
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName,true));
            int i = 1;
            while (true){
                bufferedWriter.write(Thread.currentThread().getId()+"第["+i+"]次输入;");
                System.out.println(Thread.currentThread().getId()+"第["+i+"]次输入;");
                i++;
                bufferedWriter.flush();
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            System.out.println("退出守护线程"+Thread.currentThread().getId());
        }

    }
}

class MainThread extends Thread{
    String fileName;

    volatile boolean keepRunning = true;

    public MainThread(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void run() {
        System.out.println("进入主线程"+Thread.currentThread().getId());
        DaemonThread daemon = new DaemonThread(fileName);
        Thread daemonThread = new Thread(daemon);
        daemonThread.setDaemon(true);
        daemonThread.start();
        while (keepRunning){
            Thread.yield();
        }
        System.out.println("退出主线程"+Thread.currentThread().getId());
    }
}

class DaemonTest{
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
