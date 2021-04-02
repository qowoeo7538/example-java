package org.lucas.example.foundation.thread.kata.safe.support;

public class TestSync implements Runnable {

    public int b = 100;

    public synchronized void m1() throws InterruptedException {
        b = 1000;
        // 在work线程中不论哪种情况，最后肯定会输出1000，因为此时没有修改1000的地方了。
        Thread.sleep(500);
        System.out.println("b=" + b);
    }

    /**
     * 情况A：
     * 有可能work线程已经在执行了，但是由于m2先进入了同步代码块，这个时候work进入阻塞状态，然后主线程也将会执行输出，
     * synchronized其实是在1.6之后做了很多优化的，其中就有一个自旋锁，就能保证不需要让出CPU，
     * 有可能刚好这部分时间和主线程输出重合，并且在它之前就有可能发生，b先等于1000，这个时候主线程输出其实就会有两种情况。
     * 2000 或者 1000。
     * <p>
     * 情况B：
     * 有可能work还没执行，main.m2()一执行完，他刚好就执行，这个时候还是有两种情况。b=2000或者1000
     *
     * @throws InterruptedException
     */
    public synchronized void m2() throws InterruptedException {
        Thread.sleep(250);
        b = 2000;
    }

    @Override
    public void run() {
        try {
            m1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
