package org.lucas.example.foundation.thread.demo.signal.support;

/**
 * @create: 2017-11-15
 * @description:
 */
public class PriorThread implements Runnable {
    @Override
    public void run() {
        System.out.println("********到达屏障之后我最先执行***********");
    }
}
