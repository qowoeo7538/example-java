package org.shaw.thread.concurrent.cyclicbarrier;

import org.shaw.thread.concurrent.cyclicbarrier.impl.BarrierThread;
import org.shaw.thread.concurrent.cyclicbarrier.impl.PriorThread;
import org.shaw.util.thread.DefaultThreadFactory;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

/**
 * @create: 2017-11-15
 * @description: CyclicBarrier可以用于多线程计算数据，最后合并计算结果的应用场景。
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new PriorThread());
        ExecutorService service = DefaultThreadFactory.getThreadPoolExecutor();
        for (int i = 0; i < 3; i++) {
            service.execute(new BarrierThread("线程" + (i + 1), cyclicBarrier));
        }
        DefaultThreadFactory.destroyExecutor();
    }
}
