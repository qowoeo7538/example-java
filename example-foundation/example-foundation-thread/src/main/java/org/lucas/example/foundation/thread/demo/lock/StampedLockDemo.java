package org.lucas.example.foundation.thread.demo.lock;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.util.ThreadTestUtils;

import java.util.concurrent.locks.StampedLock;

class StampedLockDemo {

    /**
     * 不可重入
     */
    @Test
    void demoReentrantLock() {
        System.out.println("StampedLock的WriteLock非正常使用:非重入锁");
        StampedLock sl = new StampedLock();
        long stamp = sl.writeLock();
        System.out.println("get write lock,stamp=" + stamp);
        stamp = sl.writeLock();
        System.out.println("get write lock,stamp=" + stamp);
        sl.unlockWrite(stamp);
    }

    @Test
    void demoWriteLock() {
        System.out.println("StampedLock的WriteLock正常使用");
        //创建StampedLock对象
        StampedLock sl = new StampedLock();
        //获取读锁，并且返回stamp
        long stamp = sl.writeLock();
        System.out.println("get write lock,stamp=" + stamp);
        //使用完毕，释放锁，但是要传入对应的stamp
        sl.unlockWrite(stamp);
        //再次获取写锁
        stamp = sl.writeLock();
        System.out.println("get write lock,stamp=" + stamp);
        //释放写锁
        sl.unlockWrite(stamp);
    }

    @Test
    void demoTryWriteLock() {
        System.out.println("StampedLock.tryWriteLock:尝试获取，如果能获取则获取锁，获取不到不阻塞");
        StampedLock sl1 = new StampedLock();
        long stamp1 = sl1.tryWriteLock();
        System.out.println("get StampedLock.tryWriteLock lock1,stamp=" + stamp1);
        long stamp2 = sl1.tryWriteLock();
        System.out.println("can not get StampedLock.tryWriteLock lock1,stamp=" + stamp2);
        long stamp3 = sl1.writeLock();
        System.out.println("can not get StampedLock.writeLock lock2,stamp=" + stamp3);
        sl1.unlockWrite(stamp1);
    }

    /**
     * 乐观锁
     */
    @Test
    void demoTryOptimistic() throws InterruptedException {
        StampedLock sl = new StampedLock();
        //乐观读
        long stamp = sl.tryOptimisticRead();
        System.out.println(Thread.currentThread().getName() + " tryOptimisticRead stamp=" + stamp);
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " run");
            long stamp131 = sl.writeLock();
            System.out.println(Thread.currentThread().getName() + " get write lock1,stamp=" + stamp131);
            sl.unlockWrite(stamp131);
            System.out.println(Thread.currentThread().getName() + " unlock write lock1,stamp=" + stamp131);
        });
        t.start();
        Thread.sleep(3000);
        // 验证是否发生过变化
        boolean validate = sl.validate(stamp);
        System.out.println(Thread.currentThread().getName() + " tryOptimisticRead validate=" + validate);
        stamp = sl.tryOptimisticRead();
        System.out.println(Thread.currentThread().getName() + " tryOptimisticRead stamp=" + stamp);
        validate = sl.validate(stamp);
        if (sl.validate(stamp)) {

        }
        ThreadTestUtils.complete(t);
    }

}
