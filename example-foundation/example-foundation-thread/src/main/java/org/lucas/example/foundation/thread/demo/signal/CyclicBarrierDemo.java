package org.lucas.example.foundation.thread.demo.signal;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.thread.demo.signal.support.BarrierThread;
import org.lucas.example.foundation.thread.demo.signal.support.PriorThread;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier可以用于多线程计算数据，
 * 最后合并计算结果的应用场景。
 */
public class CyclicBarrierDemo {

    /***
     * 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)
     */
    @Test
    public void demoCyclicBarrier() {
        var cyclicBarrier = new CyclicBarrier(3, new PriorThread());
        for (int i = 0; i < 3; i++) {
            ExampleThreadExecutor.execute(new BarrierThread("线程" + (i + 1), cyclicBarrier));
        }
        ExampleThreadExecutor.destroy();
        if (cyclicBarrier.isBroken()) {
            System.out.println("屏障已经被破坏");
        }
    }

}
