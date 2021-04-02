package org.lucas.example.foundation.thread.kata.safe;

import org.lucas.example.foundation.thread.kata.safe.support.EnergyTransferTask;
import org.lucas.example.foundation.thread.kata.safe.support.EnergySystem;

/**
 *
 */
public class ThreadInteractiveKata {
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
