package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.core.util.ThreadTestUtils;
import org.lucas.example.foundation.thread.demo.thread.support.MyThread;
import org.lucas.example.foundation.thread.demo.thread.support.Stage;
import org.lucas.example.foundation.thread.demo.thread.support.ThreadSync;

/**
 * 重排序：
 * 1.编译器优化的重排序
 * 2.指令级并行重排序（处理器优化）
 * 3.内存系统的重排序（处理器优化）
 * <p>
 * 单线程遵循as-if-serial,多线程并不一定遵循;
 */
public class ThreadDemo {

    @Test
    public void demoThread() {
        Thread stageThread = new Stage();
        ExampleThreadExecutor.submit(stageThread);
        ExampleThreadExecutor.destroy();
    }


    @Test
    public void demoOnSpinWait() {
        new Thread(() -> {
            int b = 1000000;
            long begin = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " 开始执行");
            // 让出CPU资源，使用Thread.onSpinWait();比Thread.sleep(0);性能要好。
            while (b-- > 0) {
                Thread.onSpinWait();
            }
            System.out.println(Thread.currentThread().getName() + " 执行完毕 " + (System.currentTimeMillis() - begin));
        }, "b").start();
    }

    /**
     * 线程yield()/状态测试
     *
     * @throws Exception
     */
    @Test
    public void threadState() throws Exception {
        MyThread myThread = new MyThread("my");
        // 新建一个线程 myThread, 此时线程 myThread 为new状态。
        System.out.println("1.线程名称：" + myThread.getName() + "，线程状态:" + myThread.getState());
        // 调用 myThread.start()，将线程至于runnable状态。
        myThread.start();
        myThread.join();
        System.out.println("2.线程名称：" + myThread.getName() + "，线程状态:" + myThread.getState());
    }

    @Test
    public void demoSync() throws InterruptedException {
        ThreadSync main = new ThreadSync();
        // 新建一个线程t, 此时线程t为new状态。
        Thread work = new Thread(main);
        // 调用work.start()，将线程至于runnable状态。
        work.start();
        // 此时线程work还是runnable状态，此时还没有被cpu调度，但是我们的main.m2()是我们本地的方法代码，此时一定是main.m2()先执行。
        main.m2();
        // 执行main.m2()进入 synchronized 同步代码块，开始执行代码，这里的sleep()没啥用就是混淆大家视野的，此时b=2000。
        System.out.println("main thread b=" + main.b);
        // ========================
        ThreadTestUtils.complete(work);
    }
}
