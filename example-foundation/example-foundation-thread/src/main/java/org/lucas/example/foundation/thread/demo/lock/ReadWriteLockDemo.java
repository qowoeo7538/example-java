package org.lucas.example.foundation.thread.demo.lock;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.thread.demo.lock.support.readwritelock.ReadWriteLock;

class ReadWriteLockDemo {

    /**
     * 读读锁
     */
    @Test
    void demoReadAndRead() {

        final ReadWriteLock urrw = new ReadWriteLock();

        Thread t1 = new Thread(urrw::read, "t1");
        Thread t2 = new Thread(urrw::read, "t2");
        Thread t3 = new Thread(urrw::write, "t3");
        Thread t4 = new Thread(urrw::write, "t4");

        t1.start(); // Read
        t2.start(); // Read
    }

    /**
     * 读写锁
     */
    @Test
    void demoReadAndWrite() {

        final ReadWriteLock urrw = new ReadWriteLock();
        Thread t1 = new Thread(urrw::read, "t1");
        Thread t3 = new Thread(urrw::write, "t3");

        t1.start(); // Read
        t3.start(); // Write
    }

    /**
     * 写写锁
     */
    @Test
    void demoWriteAndWrite() {
        final ReadWriteLock urrw = new ReadWriteLock();

        Thread t3 = new Thread(urrw::write, "t3");
        Thread t4 = new Thread(urrw::write, "t4");

        t3.start(); //write
        t4.start(); //write
    }

}
