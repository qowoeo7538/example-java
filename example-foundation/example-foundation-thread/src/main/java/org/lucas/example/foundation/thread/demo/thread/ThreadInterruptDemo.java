package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;

class ThreadInterruptDemo {

    @Test
    void demoInterrupt() {
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

    @Test
    void demoIsInterrupted(){
        Thread t1 = new Thread(() -> {
            //3.条件为!true=false,退出循环
            //5.如果这里更换为Thread.currentThread().isInterrupted()
            while(!Thread.currentThread().isInterrupted()){

            }
            //4.这里输出的是什么true还是false
            //6.这里输出的是什么true还是false
            System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().isInterrupted());
        });

        //1.开启
        t1.start();

        //2.中断标记设置为true
        t1.interrupt();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main is run over");
    }

}
