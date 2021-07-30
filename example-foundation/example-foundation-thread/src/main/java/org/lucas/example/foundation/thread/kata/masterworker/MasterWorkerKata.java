package org.lucas.example.foundation.thread.kata.masterworker;

import org.lucas.example.foundation.thread.kata.masterworker.support.Master;
import org.lucas.example.foundation.thread.kata.masterworker.support.Task;
import org.lucas.example.foundation.thread.kata.masterworker.support.Worker;

import java.util.concurrent.ThreadLocalRandom;

public class MasterWorkerKata {

    public static void main(String[] args) {
        // 1 创建多个Worker
        // 根据服务器的可用线程数进行创建worker
        Master master = new Master(new Worker(), Runtime.getRuntime().availableProcessors() * 2);

        for (int i = 1; i <= 20; i++) {
            Task t = new Task();
            t.setId(i);
            t.setPrice(ThreadLocalRandom.current().nextInt(1000));
            master.submit(t);
        }

        //master启动所有的worker
        master.execute();

        long start = System.currentTimeMillis();
        while (true) {
            if (master.isComplete()) {
                long end = System.currentTimeMillis() - start;
                //获取master的汇总结果
                int priceResult = master.getResult();
                System.out.println("最终结果：" + priceResult + ", 执行时间：" + end);
                break;
            }
        }
    }

}
