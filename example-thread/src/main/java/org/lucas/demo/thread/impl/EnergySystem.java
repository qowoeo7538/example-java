package org.lucas.demo.thread.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @create: 2018-01-23
 * @description:
 */
public class EnergySystem {
    /** 可重入锁 */
    private Lock lock = new ReentrantLock();

    /** 可以通过构造函数赋值 */
    private final double[] energyBoxes;
    private final Object lockObj = new Object();

    public EnergySystem(int n, double initialEnergy) {
        energyBoxes = new double[n];
        for (int i = 0; i < energyBoxes.length; i++) {
            energyBoxes[i] = initialEnergy;
        }
    }

    public void transfer(int form, double amount, int to) {
        // synchronized 加锁时将清空工作内存中共享变量的值，从而使用共享变量时需要从主内存中重新读取最新值（需要对同一个对象的锁）
        synchronized (lockObj) {
            // if(energyBoxes[form] < amount) return;
            while (energyBoxes[form] < amount) {
                try {
                    // 放弃已经持有的锁然后等待，保证条件不满足时，不会持续竞争锁资源
                    lockObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(Thread.currentThread().getName());
            energyBoxes[form] -= amount;
            System.out.printf("从%d转移%10.2f单位能量到%d", form, amount, to);
            energyBoxes[to] += amount;
            System.out.printf("能量总和：%10.2f%n", getToTALEnergies());

            // 唤醒所有在lockObj对象上等待的线程
            lockObj.notifyAll();
        }   // synchronized 解锁前将工作内存中共享的变量的最新值刷新到主内存中
    }

    public double getToTALEnergies() {
        double sum = 0;
        for (double amount : energyBoxes) {
            sum += amount;
        }
        return sum;
    }

    public int getBoxAmount() {
        return energyBoxes.length;
    }
}
