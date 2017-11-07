package org.shaw.thread.concurrent;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by joy on 17-2-20.
 * CyclicBarrier可以用于多线程计算数据，最后合并计算结果的应用场景。
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new PriorThread());
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            service.execute(new BarrierThread("线程"+(i+1),cyclicBarrier));
        }
        service.shutdown();
    }
}

class BarrierThread implements Runnable{

    CyclicBarrier cyclicBarrier;
    String name;

    public BarrierThread(String name,CyclicBarrier cyclicBarrier){
//        Thread.currentThread().setName(name);  一个线程作用范围是整个方法
//        System.out.println(Thread.currentThread().getName());
        this.name = name;
        this.cyclicBarrier = cyclicBarrier;
    }

    public void run() {
        try {
            Thread.currentThread().setName(name);
            Thread.sleep((int)Math.random()*10000);
            System.out.println(Thread.currentThread().getName()+"即将到达集合地点1，当前已有" + cyclicBarrier.getNumberWaiting()+ "个已经到达，正在等候");
            cyclicBarrier.await();

            Thread.sleep((int)Math.random()*10000);
            System.out.println(Thread.currentThread().getName()+"即将到达集合地点2，当前已有" + cyclicBarrier.getNumberWaiting()+ "个已经到达，正在等候");
            cyclicBarrier.await();

            Thread.sleep((int)Math.random()*10000);
            System.out.println(Thread.currentThread().getName()+"即将到达集合地点3，当前已有" + cyclicBarrier.getNumberWaiting()+ "个已经到达，正在等候");
            cyclicBarrier.await();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class PriorThread implements Runnable{

    public void run() {
        System.out.println("********到达屏障之后我最先执行***********");
    }
}



