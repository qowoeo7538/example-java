package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.core.util.ThreadTestUtils;
import org.lucas.example.foundation.thread.demo.thread.support.MyThread;
import org.lucas.example.foundation.thread.demo.thread.support.Stage;
import org.lucas.example.foundation.thread.demo.thread.support.ThreadSync;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 重排序：
 * 1.编译器优化的重排序
 * 2.指令级并行重排序（处理器优化）
 * 3.内存系统的重排序（处理器优化）
 * <p>
 * 单线程遵循as-if-serial,多线程并不一定遵循;
 */
class ThreadDemo {

    @Test
    void demoThread() {
        Thread stageThread = new Stage();
        ExampleThreadExecutor.submit(stageThread);
        ExampleThreadExecutor.destroy();
    }


    @Test
    void demoOnSpinWait() {
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
    void threadState() throws Exception {
        MyThread myThread = new MyThread("my");
        // 新建一个线程 myThread, 此时线程 myThread 为new状态。
        System.out.println("1.线程名称：" + myThread.getName() + "，线程状态:" + myThread.getState());
        // 调用 myThread.start()，将线程至于runnable状态。
        myThread.start();
        myThread.join();
        System.out.println("2.线程名称：" + myThread.getName() + "，线程状态:" + myThread.getState());
    }

    @Test
    void demoSync() throws InterruptedException {
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

    @Test
    void demoDaemonThread() {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("= =!");
            }
        });

        // 打开和关闭这个设置观察JVM进程是否终止
        thread.setDaemon(true);
        thread.start();

        //输出线程是否为守护线程
        System.out.println(thread.getName() + " is daemon? " + thread.isDaemon());
        System.out.println(Thread.currentThread().getName() + " is daemon? " + Thread.currentThread().isDaemon());

        System.out.println("main is over");
    }

    @Test
    void demoInterrupt1() {
        Thread t1 = new Thread(() -> {
            // 2. 开始执行循环
            for (int i = 0; i < 999999; i++) {
                // 3. 判断是否为中断状态,如果是中断则退出循环
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " interrupted");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + i + " is running");
            }
        });

        // 1. 启动
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. 调用中断,是否会中断死循环？
        t1.interrupt();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t1.getState());
    }

}
