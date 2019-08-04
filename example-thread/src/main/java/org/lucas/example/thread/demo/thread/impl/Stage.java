package org.lucas.example.thread.demo.thread.impl;

/**
 * @create: 2018-01-23
 * @description:
 */
public class Stage extends Thread {
    @Override
    public void run() {
        ArmyRunnable armyTaskOfSuiDynasty = new ArmyRunnable();
        ArmyRunnable armyTaskOfRevolt = new ArmyRunnable();

        Thread armyOfSuiDynasty = new Thread(armyTaskOfSuiDynasty, "隋军");
        Thread armOfRevolt = new Thread(armyTaskOfRevolt, "起义军");

        //设置优先级 最大为10 最小为1 默认为5;
        armyOfSuiDynasty.setPriority(10);
        armyOfSuiDynasty.setPriority(1);

        armyOfSuiDynasty.start();
        armOfRevolt.start();

        try {
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("英雄人物上场");
        Thread mrCheng = new KeyPersonThread();
        mrCheng.setName("程咬金");

        //停止其他线程（推荐方式）
        armyTaskOfSuiDynasty.keepRunning = false;
        armyTaskOfRevolt.keepRunning = false;

        //保证其他线程已经结束
        while (!armyOfSuiDynasty.isAlive() && !armOfRevolt.isAlive()) {
            Thread.yield();
        }
        //关键任人物线程开始执行
        mrCheng.start();
        try {
            //等待该工作线程终止,主线程才继续运行=====>”战争结束“
            mrCheng.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("当前线程的线程组中活动线程的数目:" + Thread.activeCount());
        System.out.println("战争结束");
    }
}
