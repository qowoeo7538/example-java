package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.util.ThreadTestUtils;
import org.lucas.example.foundation.thread.demo.thread.support.InheritableThread;

public class ThreadLocalDemo {

    /**
     * 引用:Thread Ref -> Thread -> ThreaLocalMap -> Entry -> value
     */
    @Test
    public void demoThreadLocal() throws Exception {
        final ThreadLocal<Integer> th = new ThreadLocal<>();

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

    @Test
    void demoInheritableThreadLocal() throws Exception {
        //多个线程之间读取副本, 父子线程之间复制传递
        InheritableThreadLocal<String> tl = new InheritableThreadLocal<>();

        tl.set("Kevin是一个自由讲师");

        Thread t0 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " get tl is : " + tl.get());
            tl.set("After Set The Value Change to " + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " get tl is : " + tl.get());
        });

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " get tl is : " + tl.get());
            tl.set("After Set The Value Change to " + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " get tl is : " + tl.get());
        });

        t0.start();

        Thread.sleep(1000);

        t1.start();

        System.out.println(Thread.currentThread().getName() + " get tl is : " + tl.get());

        ThreadTestUtils.complete(t0, t1);
    }

    @Test
    void demoInheritableThreadLocal2() {
        InheritableThreadLocal<Integer> tl = new InheritableThreadLocal<>();
        InheritableThread.tl = (tl);

        tl.set(1001);
        Thread t1 = new InheritableThread();
        t1.start();

        tl.set(2002);
        Thread t2 = new InheritableThread();
        t2.start();
        ThreadTestUtils.complete(t1, t2);
    }

}
