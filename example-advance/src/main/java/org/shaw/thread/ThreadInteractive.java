package org.shaw.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by joy on 17-2-13.
 */
public class ThreadInteractive {
    public static final int BOX_AMOUNT = 100;

    public static final double INTTIAL_EENERGY = 1000;

    public static void main(String[] args) {
        EnergySystem eng = new EnergySystem(BOX_AMOUNT,INTTIAL_EENERGY);
        for (int i = 0; i < BOX_AMOUNT; i++) {
            EnergyTransferTask task = new EnergyTransferTask(eng,i,INTTIAL_EENERGY);
            Thread t = new Thread(task,"TransferThread_"+i);
            t.start();
        }

    }
}

class EnergySystem{
    private Lock lock = new ReentrantLock(); // 可重入锁

    private final double[] energyBoxes;  // 可以通过构造函数赋值
    private final Object lockObj = new Object();

    public EnergySystem(int n,double initialEnergy){
        energyBoxes = new double[n];
        for (int i = 0; i < energyBoxes.length; i++) {
            energyBoxes[i] = initialEnergy;
        }
    }

    public  void transfer(int form,double amount,int to){
        synchronized (lockObj){  // synchronized 加锁时将清空工作内存中共享变量的值，从而使用共享变量时需要从主内存中重新读取最新值（需要对同一个对象的锁）
            // if(energyBoxes[form] < amount) return;
            while (energyBoxes[form] < amount){
                try {
                    lockObj.wait(); // 放弃已经持有的锁然后等待，保证条件不满足时，不会持续竞争锁资源
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            System.out.print(Thread.currentThread().getName());
            energyBoxes[form]-=amount;
            System.out.printf("从%d转移%10.2f单位能量到%d",form,amount,to);
            energyBoxes[to]+=amount;
            System.out.printf("能量总和：%10.2f%n",getToTALEnergies());

            lockObj.notifyAll(); //唤醒所有在lockObj对象上等待的线程
        }  // synchronized 解锁前将工作内存中共享的变量的最新值刷新到主内存中
    }

    public double getToTALEnergies(){
        double sum = 0;
        for (double amount : energyBoxes) {
            sum += amount;
        }
        return sum;
    }

    public int getBoxAmount(){
        return energyBoxes.length;
    }
}

class EnergyTransferTask implements Runnable{

    private EnergySystem energySystem;
    private int fromBox;
    private double maxAmount;
    private final int DELAY = 10;

    public EnergyTransferTask(EnergySystem energySystem,int from,double max){
        this.energySystem = energySystem;
        this.fromBox = from;
        this.maxAmount = max;
    }

    public void run() {
        try {
            while (true){
                int toBox = (int) (energySystem.getBoxAmount()* Math.random());
                double amount = maxAmount*Math.random();
                energySystem.transfer(fromBox,amount,toBox);
                Thread.sleep((int)(DELAY*Math.random()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
