package org.lucas.example.algorithm.timingwheel;

import org.junit.jupiter.api.Test;
import org.lucas.example.algorithm.timingwheel.support.timer.Timer;
import org.lucas.example.algorithm.timingwheel.support.timer.TimerTask;

import java.util.concurrent.CountDownLatch;

class TimingWheelDemo {

    static int inCount = 0;

    static int runCount = 0;

    @Test
    void demoTimer() {
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        Timer timer = new Timer();
        for (int i = 1; i <= 1000; i++) {
            TimerTask timerTask = new TimerTask(i, () -> {
                countDownLatch.countDown();
                int index = addRun();
                System.out.println(index + "----------执行");
            });
            timer.addTask(timerTask);
            System.out.println(i + "++++++++++加入");
            inCount++;
        }
        TimerTask timerTask = new TimerTask(5000, () -> {
            countDownLatch.countDown();
            int index = addRun();
            System.out.println(index + "----------执行");
        });
        timer.addTask(timerTask);
        try {
            countDownLatch.await();
            System.out.println("inCount" + inCount);
            System.out.println("runCount" + runCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static int addRun() {
        runCount++;
        return runCount;
    }

}
