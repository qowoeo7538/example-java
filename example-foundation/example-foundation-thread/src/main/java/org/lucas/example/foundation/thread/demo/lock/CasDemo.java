package org.lucas.example.foundation.thread.demo.lock;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.thread.demo.lock.support.cas.CasThread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS：乐观锁的一种，通过判断当前值是否符合预期值来决定是否进行修改。
 */
class CasDemo {

    @Test
    void demoCas() {
        final AtomicInteger count = new AtomicInteger(0);
        for (int j = 0; j < 100; j++) {
            ExampleThreadExecutor.execute(new CasThread(count));
        }
        ExampleThreadExecutor.destroy();
        System.out.println(count.get());
    }

    /**
     * 通过版本号解决 ABA 问题
     */
    @Test
    void demoAtomicStampedReference() {
        // 主内存共享变量，初始值为1，版本号为1
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);
        ExampleThreadExecutor.execute(() -> {
            // 返回当前值的版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 第1次版本号：" + stamp + " 值为：" + atomicStampedReference.getReference());
            // 休眠5s,确保t2执行完ABA操作
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // t2将版本号改为了3,cas失败
            boolean b = atomicStampedReference.compareAndSet(1, 10, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + " CAS是否成功：" + b);
            System.out.println(Thread.currentThread().getName() + " 当前最新版本号：" + atomicStampedReference.getStamp() + " 最新值为：" + atomicStampedReference.getReference());
        });

        ExampleThreadExecutor.execute(() -> {
            // 返回当前值的版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 第1次版本号：" + stamp + " 值为：" + atomicStampedReference.getReference());
            // 休眠，修改前确保t1也拿到同样的副本，初始值为1
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 将副本改为20，再写入，紧接着又改为1，写入，每次提升一个版本号，中间t1没介入
            atomicStampedReference.compareAndSet(1, 20, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + " 第2次版本号：" + atomicStampedReference.getStamp() + " 值为：" + atomicStampedReference.getReference());
            atomicStampedReference.compareAndSet(20, 1, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + " 第3次版本号：" + atomicStampedReference.getStamp() + " 值为：" + atomicStampedReference.getReference());
        });
        ExampleThreadExecutor.destroy();
    }

    @Test
    void demoAtomicStampedReference2() throws Exception {
        AtomicStampedReference<Integer> atomic = new AtomicStampedReference<>(100, 0);

        Thread t0 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                boolean sucess = atomic.compareAndSet(100, 101, atomic.getStamp(), atomic.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + " set 100>101 : " + sucess);
                sucess = atomic.compareAndSet(101, 100, atomic.getStamp(), atomic.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + " set 101>100 : " + sucess);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t0.start();

        Thread t1 = new Thread(() -> {
            try {
                int stamp = atomic.getStamp();
                System.out.println(Thread.currentThread().getName() + " 修改之前 : " + stamp);
                TimeUnit.SECONDS.sleep(2);
                int stamp1 = atomic.getStamp();
                System.out.println(Thread.currentThread().getName() + " 等待两秒之后,版本被t0线程修改为 : " + stamp1);

                // 一下两次修改都不会成功,因为版本不符,虽然期待值是相同的,因此解决了ABA问题
                boolean success = atomic.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println(Thread.currentThread().getName() + " set 100>101 使用错误的时间戳: " + success);
                success = atomic.compareAndSet(101, 100, stamp, stamp + 1);
                System.out.println(Thread.currentThread().getName() + " set 101>100 使用错误的时间戳: " + success);

                // 以下修改是成功的,因为使用了正确的版本号,正确的期待值
                success = atomic.compareAndSet(100, 101, stamp1, stamp1 + 1);
                System.out.println(Thread.currentThread().getName() + " set 100>101 使用正确的时间戳: " + success);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();

        t0.join();
        t1.join();

        System.out.println("main is over");
    }

    /**
     * 原子性修改对象属性值,变量需要保证可见性
     */
    @Test
    void demoFieldUpdater() {
        AtomicReferenceFieldUpdater<ValueDTO, String> updater = AtomicReferenceFieldUpdater.newUpdater(ValueDTO.class, String.class, "name");
        ValueDTO value = new ValueDTO();
        System.out.println(updater.get(value));
        while (!updater.compareAndSet(value, value.name, "test")) {
            System.out.println(updater.get(value));
        }
    }
}
