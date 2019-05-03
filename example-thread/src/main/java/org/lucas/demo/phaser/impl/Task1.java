package org.lucas.demo.phaser.impl;

import java.util.concurrent.Phaser;

public class Task1 implements Runnable {
    private final int id;
    private final Phaser phaser;

    public Task1(int id, Phaser phaser) {
        this.id = id;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        phaser.arriveAndAwaitAdvance(); //等待其他线程准备
        System.out.println("in Task.run(), phase number: " + phaser.getPhase() + ", id: " + this.id);
    }
}
