package org.lucas.example.foundation.thread.kata.safe.impl;


import org.lucas.example.foundation.thread.kata.safe.impl.EnergySystem;

/**
 * @create: 2018-01-23
 * @description:
 */
public class EnergyTransferTask implements Runnable {
    private EnergySystem energySystem;
    private int fromBox;
    private double maxAmount;
    private final int DELAY = 10;

    public EnergyTransferTask(EnergySystem energySystem, int from, double max) {
        this.energySystem = energySystem;
        this.fromBox = from;
        this.maxAmount = max;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int toBox = (int) (energySystem.getBoxAmount() * Math.random());
                double amount = maxAmount * Math.random();
                energySystem.transfer(fromBox, amount, toBox);
                Thread.sleep((int) (DELAY * Math.random()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
