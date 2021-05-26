package org.lucas.example.foundation.thread.demo.lock.support;

public class SynchronizedExceptionDemo {

    private int i = 0;

    public synchronized void run(){
        while(true){
            i++;
            System.out.println(Thread.currentThread().getName()+"-run>i="+i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int k = 10/0;

            if (i == 10) {
                throw new RuntimeException();
            }
        }
    }

    public synchronized void get(){
        System.out.println(Thread.currentThread().getName()+"-get>i="+i);
    }


}
