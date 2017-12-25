package org.shaw.thread.concurrent.cyclicbarrier;

import org.shaw.core.task.DefaultThreadFactory;
import org.shaw.thread.concurrent.cyclicbarrier.impl.BarrierThread;
import org.shaw.thread.concurrent.cyclicbarrier.impl.PriorThread;
import org.shaw.util.thread.DefaultThreadFactory;

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
            DefaultThreadFactory.execute(new BarrierThread("线程" + (i + 1), cyclicBarrier));
        }
        DefaultThreadFactory.destroy();
    }
}
