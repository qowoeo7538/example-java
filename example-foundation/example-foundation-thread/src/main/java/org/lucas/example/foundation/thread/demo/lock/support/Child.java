package org.lucas.example.foundation.thread.demo.lock.support;

public class Child extends Parent{

    public synchronized void runChild() {
        try {
            while (i > 0) {
                i--;
                System.out.println("Child>>>>i=" + i);
                Thread.sleep(100);
                //调用父类中的synchronized方法不会引起死锁
                this.runParent();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
