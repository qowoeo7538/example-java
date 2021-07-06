package org.lucas.example.foundation.thread.demo.signal;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.core.util.ThreadTestUtils;
import org.lucas.example.foundation.thread.demo.signal.support.ConcurrencyThrottleSupportTest;
import org.lucas.example.foundation.thread.demo.signal.support.DoSomething;
import org.lucas.example.foundation.thread.demo.signal.support.EnergySystem;
import org.lucas.example.foundation.thread.demo.signal.support.EnergyTransferTask;
import org.lucas.example.foundation.thread.demo.signal.support.NotifyAll;

/**
 * synchronized
 * <p>
 * notifyAll()唤醒的睡眠线程,只有当执行完synchronized代码块才会继续唤醒其他线程
 */
public class WaitNotifyDemo {

    /**
     * notify 只是选择一个wait状态线程进行通知，并使它获得该对象上的锁，但不惊动其他同样在等待被该对象notify的线程们，当第一个线程运行完毕以
     * 后释放对象上的锁此时如果该对象没有再次使用notify语句，则即便该对象已经空闲，其他wait状态等待的线程由于没有得到该对象的通知，
     * 继续处在wait状态，直到这个对象发出一个notify或notifyAll，它们等待的是被notify或notifyAll，而不是锁。
     */
    @Test
    public void notifyTest() {
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(new DoSomething("线程" + i));
        }
        ExampleThreadExecutor.destroy();
    }

    /**
     * notifyAll 使所有原来在该对象上等待被notify的线程统统退出wait的状态，变成等待该对象上的锁，一旦该对象被解锁，他们就会去竞争。
     */
    @Test
    public void demoNotifyAll() throws Exception {
        final NotifyAll demo = new NotifyAll();

        Thread t1 = new Thread(demo::run2);
        t1.start();

        Thread t2 = new Thread(demo::run3);
        t2.start();

        Thread.sleep(1000L);

        Thread t3 = new Thread(demo::run1);
        t3.start();

        ThreadTestUtils.complete(t1, t2, t3);
    }

    /**
     * notifyAll 使所有原来在该对象上等待被notify的线程统统退出wait的状态，变成等待该对象上的锁，一旦该对象被解锁，他们就会去竞争。
     */
    @Test
    public void demoNotifyAll1() {
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(new ConcurrencyThrottleSupportTest("线程" + i));
        }
        ExampleThreadExecutor.destroy();
    }

    /**
     * notifyAll 使所有原来在该对象上等待被notify的线程统统退出wait的状态，变成等待该对象上的锁，一旦该对象被解锁，他们就会去竞争。
     */
    @Test
    public void demoNotifyAll2() {
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
