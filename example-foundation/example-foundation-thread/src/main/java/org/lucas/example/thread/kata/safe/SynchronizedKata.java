package org.lucas.example.thread.kata.safe;

import org.junit.jupiter.api.Test;
import org.lucas.example.thread.kata.safe.impl.TestSync;
import org.lucas.example.foundation.core.util.ThreadTestUtils;

/**
 * 线程的几个状态:new,runnable(Thread.start()),running,blocking(Thread.Sleep())
 */
public class SynchronizedKata {

    @Test
    public void testSync() throws InterruptedException {
        TestSync main = new TestSync();
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
