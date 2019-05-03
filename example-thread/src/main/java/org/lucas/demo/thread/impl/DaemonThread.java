package org.lucas.demo.thread.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @create: 2018-01-23
 * @description:
 */
public class DaemonThread implements Runnable {
    String fileName;

    public DaemonThread(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        System.out.println("进入守护线程" + Thread.currentThread().getId());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            int i = 1;
            while (true) {
                bufferedWriter.write(Thread.currentThread().getId() + "第[" + i + "]次输入;");
                System.out.println(Thread.currentThread().getId() + "第[" + i + "]次输入;");
                i++;
                bufferedWriter.flush();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
