package org.shaw.base.thread;

import org.shaw.base.thread.impl.EnergySystem;
import org.shaw.base.thread.impl.EnergyTransferTask;

/**
 * @create: 2018-01-23
 * @description:
 */
public class ThreadInteractiveDemo {
    public static final int BOX_AMOUNT = 100;

    public static final double INTTIAL_EENERGY = 1000;

    public static void main(String[] args) {
        EnergySystem eng = new EnergySystem(BOX_AMOUNT, INTTIAL_EENERGY);
        for (int i = 0; i < BOX_AMOUNT; i++) {
            EnergyTransferTask task = new EnergyTransferTask(eng, i, INTTIAL_EENERGY);
            Thread t = new Thread(task, "TransferThread_" + i);
            t.start();
        }
    }
}
