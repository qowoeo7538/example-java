package org.lucas.example.foundation.thread.demo.lock.support;

public class Parent {

    public int i = 10;

    public synchronized void runParent() {
        try {
            i--;
            System.out.println("Parent>>>>i=" + i);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
