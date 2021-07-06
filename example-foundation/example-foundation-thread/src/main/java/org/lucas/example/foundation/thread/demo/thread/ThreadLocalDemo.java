package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.util.ThreadTestUtils;

public class ThreadLocalDemo {

    @Test
    public void demoThreadLocal() throws Exception {
        final ThreadLocal<Integer> th = new ThreadLocal<Integer>();

        var t1 = new Thread(() -> {
            try {
                th.set(100);
                System.out.println("t1 set th=" + th.get());
                Thread.sleep(2000);
                System.out.println("t1 get th=" + th.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();

        Thread.sleep(1000);

        var t2 = new Thread(() -> {
            Integer ele = th.get();
            System.out.println("t2 get th=" + ele);
            th.set(200);
            System.out.println("t2 get th=" + th.get());
        });
        t2.start();

        ThreadTestUtils.complete(t1, t2);
    }

}
