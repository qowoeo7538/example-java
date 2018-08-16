package org.shaw.api.phaser.impl;

import java.util.concurrent.Phaser;

public class Task3 implements Runnable {
    private final int id;
    private final Phaser phaser;

    public Task3(int id, Phaser phaser) {
        this.id = id;
        this.phaser = phaser;
        //初始化加入Phaser
        this.phaser.register();
    }

    @Override
    public void run() {
        while (!phaser.isTerminated()) {
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                // NOP
            }
            System.out.println("in Task.run(), phase: " + phaser.getPhase() + ",id: " + this.id);
            phaser.arriveAndAwaitAdvance();
        }
    }
}
