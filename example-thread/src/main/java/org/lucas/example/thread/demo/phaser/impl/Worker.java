package org.lucas.example.thread.demo.phaser.impl;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Worker extends Thread {
    private Phaser phaser;

    public Worker(String name, Phaser phaser) {
        this.setName(name);
        this.phaser = phaser;
        // 把当前线程注册到 Phaser 中.
        phaser.register();
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println("current order is: " + i + "||" + getName());
            if (i == 3) {
                // 如果三个订单都处理完成，则解除注销.
                phaser.arriveAndDeregister();
            } else {
                // 如果还有其它订单未处理完，则等待其它订单处理完毕.
                phaser.arriveAndAwaitAdvance();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
