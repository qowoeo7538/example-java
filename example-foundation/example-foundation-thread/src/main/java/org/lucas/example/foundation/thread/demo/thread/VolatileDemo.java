package org.lucas.example.foundation.thread.demo.thread;

import org.junit.Test;
import org.lucas.example.foundation.thread.demo.thread.support.Volatile;

public class VolatileDemo {

    /**
     * 使用 volatile 通信,但是 volatile 无法保证原子性
     */
    @Test
    public void demoVolatile() throws Exception {
        final Volatile demo = new Volatile();
        Thread t1 = new Thread(demo::put, "t1");

        Thread t2 = new Thread(demo::get, "t2");
        t2.start();
        t1.start();

        t1.join();
        t2.join();
    }

}
