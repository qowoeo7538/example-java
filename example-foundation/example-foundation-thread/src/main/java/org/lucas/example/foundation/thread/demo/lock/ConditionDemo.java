package org.lucas.example.foundation.thread.demo.lock;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.util.ThreadTestUtils;
import org.lucas.example.foundation.thread.demo.lock.support.condition.ConditionLock1;
import org.lucas.example.foundation.thread.demo.lock.support.condition.ConditionLock2;
import org.lucas.example.foundation.thread.demo.lock.support.condition.Runner;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ConditionDemo {

    @Test
    void demoAQS() {
        ReentrantLock rl = new ReentrantLock();
        Condition condition = rl.newCondition();
        Thread t1 = new Runner(rl, condition);
        Thread t2 = new Runner(rl, condition);
        Thread t3 = new Runner(rl, condition);

        t1.start();
        t2.start();
        t3.start();

        ThreadTestUtils.complete(t1, t2, t3);
    }

    @Test
    void demoCondition1() {
        final ConditionLock1 uc = new ConditionLock1();
        Thread t1 = new Thread(uc::run1, "t1");
        Thread t2 = new Thread(uc::run2, "t2");

        t1.start();
        t2.start();

        ThreadTestUtils.complete(t1, t2);
    }

    @Test
    void demoCondition2() {
        final ConditionLock2 uc = new ConditionLock2();
        Thread t1 = new Thread(uc::run1, "t1");
        Thread t2 = new Thread(uc::run2, "t2");
        Thread t3 = new Thread(uc::run3, "t3");
        Thread t4 = new Thread(uc::run4, "t4");
        Thread t5 = new Thread(uc::run5, "t5");
        t1.start();
        t2.start();
        t3.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t4.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t5.start();

        ThreadTestUtils.complete(t1, t2, t3, t4, t5);
    }

}
