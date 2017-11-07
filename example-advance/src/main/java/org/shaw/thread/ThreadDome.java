package org.shaw.thread;

/**
 * 重排序：1.编译器优化的重排序  2.指令级并行重排序（处理器优化）  3.内存系统的重排序（处理器优化）
 * 单线程遵循as-if-serial,多线程并不一定遵循;
 * Created by joy on 17-2-12.
 */
public class ThreadDome {
    public static void main(String[] args) {
        Thread stageThread = new Stage();
        stageThread.start();
    }
}


class Stage extends Thread{
    @Override
    public void run() {
        ArmyRunnable armyTaskOfSuiDynasty = new ArmyRunnable();
        ArmyRunnable armyTaskOfRevolt = new ArmyRunnable();

        Thread armyOfSuiDynasty = new Thread(armyTaskOfSuiDynasty,"隋军");
        Thread armOfRevolt = new Thread(armyTaskOfRevolt,"起义军");

        //设置优先级 最大为10 最小为1 默认为5;
        armyOfSuiDynasty.setPriority(10);
        armyOfSuiDynasty.setPriority(1);

        armyOfSuiDynasty.start();
        armOfRevolt.start();

        try {
            Thread.sleep(50);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("英雄人物上场");
        Thread mrCheng = new KeyPersonThread();
        mrCheng.setName("程咬金");

        //停止其他线程（推荐方式）
        armyTaskOfSuiDynasty.keepRunning = false;
        armyTaskOfRevolt.keepRunning = false;

        while ( !armyOfSuiDynasty.isAlive() && !armOfRevolt.isAlive()){ //保证其他线程已经结束
            Thread.yield();
        }
        //关键任人物线程开始执行
        mrCheng.start();
        try {
             mrCheng.join(); //等待该工作线程终止,才继续运行=====>”战争结束“
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("当前线程的线程组中活动线程的数目:"+Thread.activeCount());

        System.out.println("战争结束");
    }
}

class KeyPersonThread extends Thread{
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName()+"左突右杀隋军["+i+"]次");
        }
    }
}

class ArmyRunnable implements Runnable{

    //当结果依赖当前的值，如 keepRunning++ ，keepRunning+=5，不能使用。
    //该变量在不变式中有其它volatile变量不能使用。
    volatile boolean keepRunning = true; //每次写这个变量时会将该变量工作内存的值写入主内存中。读取时，会将主内存中的变量读取到工作内存中

    public void run() {
        while (keepRunning){
            for (int i = 1; i <=5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"进攻对方["+i+"]次");
                Thread.yield(); //暂停当前正在执行的线程对象，并随机执行线程
            }
        }
        System.out.println(Thread.currentThread().getName()+"结束了战斗");  //可以当作线程结束后的清理工作
    }
}
