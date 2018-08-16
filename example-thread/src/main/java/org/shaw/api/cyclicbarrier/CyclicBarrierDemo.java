package org.shaw.api.cyclicbarrier;

import org.shaw.api.cyclicbarrier.impl.BarrierThread;
import org.shaw.api.cyclicbarrier.impl.PriorThread;
import org.shaw.core.task.ExampleThreadExecutor;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier可以用于多线程计算数据，
 * 最后合并计算结果的应用场景。
 */
public class CyclicBarrierDemo {

    private final static int COUNT = 3;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(COUNT, new PriorThread());
        for (int i = 0; i < COUNT; i++) {
            ExampleThreadExecutor.execute(new BarrierThread("线程" + (i + 1), cyclicBarrier));
        }
        ExampleThreadExecutor.destroy();
    }
}
