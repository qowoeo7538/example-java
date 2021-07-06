package org.lucas.example.foundation.thread.demo.signal;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.core.util.ThreadTestUtils;
import org.lucas.example.foundation.thread.demo.signal.support.DoSomething;
import org.lucas.example.foundation.thread.demo.signal.support.EnergySystem;
import org.lucas.example.foundation.thread.demo.signal.support.EnergyTransferTask;
import org.lucas.example.foundation.thread.demo.signal.support.NotifyAllTest;

/**
 * synchronized
 * <p>
 * notifyAll()唤醒的睡眠线程,只有当执行完synchronized代码块才会继续唤醒其他线程
 */
public class WaitNotifyDemo {

    /**
     * spring源码测试
     * <p>
     * notify();
     */
    @Test
    public void notifyTest() {
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(new DoSomething("线程" + i));
        }
        ExampleThreadExecutor.destroy();
    }

    /**
     * spring源码测试
     * <p>
     * notifyAll();
     */
    @Test
    public void DemoNotifyAll() {
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(new NotifyAllTest("线程" + i));
        }
        ExampleThreadExecutor.destroy();
    }

    @Test
    public void demoNotifyAll() {
        final var boxAmount = 100;
        final double initiallyEnergy = 1000;
        var eng = new EnergySystem(boxAmount, initiallyEnergy);
        var threads = new Thread[boxAmount];
        for (var i = 0; i < boxAmount; i++) {
            EnergyTransferTask task = new EnergyTransferTask(eng, i, initiallyEnergy);
            var t = new Thread(task, "TransferThread_" + i);
            t.start();
            threads[i] = t;
        }
        ThreadTestUtils.complete(threads);
    }
}
