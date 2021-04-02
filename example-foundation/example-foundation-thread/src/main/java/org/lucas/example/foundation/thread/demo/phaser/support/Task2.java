package org.lucas.example.foundation.thread.demo.phaser.support;

import java.util.concurrent.Phaser;

public class Task2 implements Runnable {
    private final int id;
    private final Phaser phaser;

    public Task2(int id, Phaser phaser) {
        this.id = id;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }

            System.out.println("in Task.run(), phase: " + phaser.getPhase() + ", id: " + this.id);
            phaser.arriveAndAwaitAdvance();
        } while (!phaser.isTerminated());
    }
}
