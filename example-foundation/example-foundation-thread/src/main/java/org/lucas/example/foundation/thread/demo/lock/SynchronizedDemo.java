package org.lucas.example.foundation.thread.demo.lock;

import org.junit.Test;
import org.lucas.example.foundation.thread.demo.lock.support.ObjectLock;
import org.lucas.example.foundation.thread.demo.lock.support.SynchronizedExceptionDemo;

import static org.lucas.example.foundation.thread.demo.lock.support.ClassLock.add;

/**
 * 对象锁 和 类锁
 * (多个对象多把锁、相互不影响)
 * (访问静态变量则需要多个对象使用一把锁、那么需要将锁升级为类锁)
 */
public class SynchronizedDemo {

    /**
     * 为什么要用final修饰:
     * <p>
     * 内部类对象的生命周期会超过局部变量的生命周期。
     * 局部变量的生命周期：当该方法被调用时，该方法中的局部变量在栈中被创建，当方法调用结束时，退栈，这些局部变量全部死亡。
     * 而内部类对象生命周期与其它类一样：自创建一个匿名内部类对象，系统为该对象分配内存，直到没有引用变量指向分配给该对象的内存，它才会死亡(被JVM垃圾回收)。
     * 所以完全可能出现的一种情况是：成员方法已调用结束，局部变量已死亡，但匿名内部类的对象仍然活着。
     * <p>
     * 匿名内部类对象可以访问同一个方法中被定义为final类型的局部变量。
     * 定义为final后，编译程序的实现方法：对于匿名内部类对象要访问的所有final类型局部变量，都拷贝成为该对象中的一个数据成员。
     * 这样，即使栈中局部变量已死亡，但被定义为final类型的局部变量的值永远不变，因而匿名内部类对象在局部变量死亡后，照样可以访问final类型的局部变量，因为它自己拷贝了一份，且与原局部变量的值始终一致。
     */
    @Test
    public void testObjectLock() throws Exception {
        final ObjectLock thread1 = new ObjectLock();
        final ObjectLock thread2 = new ObjectLock();
        var t1 = new Thread(() -> thread1.add(), "thread1");

        var t2 = new Thread(() -> {
            //1、同一个对象、同一把锁
            //thread1.add();
            thread2.add();
        }, "thread2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    /**
     * 类锁
     */
    @Test
    public void testClassLock() throws Exception {
        Thread t1 = new Thread(() -> {
            // 1 同一个对象、同一把锁
            // 内部类无法访问非final对象
            add();
        }, "thread1");

        Thread t2 = new Thread(() -> {
            //1、同一个对象、同一把锁
            //thread1.add();
            add();
        }, "thread2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    /***
     *
     * 抛出异常会对锁进行释放
     */
    @Test
    public void demoSynchronizedException() throws Exception {
        final SynchronizedExceptionDemo synchronizedException = new SynchronizedExceptionDemo();
        Thread t1 = new Thread(synchronizedException::run, "t1");
        t1.start();
        //保证t1线程先执行
        Thread.sleep(1000);
        Thread t2 = new Thread(synchronizedException::get, "t2");
        t2.start();

        t1.join();
        t2.join();

    }

}
