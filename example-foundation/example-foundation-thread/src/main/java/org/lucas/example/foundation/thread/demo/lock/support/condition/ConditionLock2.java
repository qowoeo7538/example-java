package org.lucas.example.foundation.thread.demo.lock.support.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionLock2 {


    private ReentrantLock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();

    public void run1(){
        try {
            lock.lock();
            System.out.println("当前线程：" +Thread.currentThread().getName() + "进入方法run1等待..");
            c1.await();
            System.out.println("当前线程：" +Thread.currentThread().getName() + "方法run1继续..");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void run2(){
        try {
            lock.lock();
            System.out.println("当前线程：" +Thread.currentThread().getName() + "进入方法run2等待..");
            c1.await();
            System.out.println("当前线程：" +Thread.currentThread().getName() + "方法run2继续..");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void run3(){
        try {
            lock.lock();
            System.out.println("当前线程：" +Thread.currentThread().getName() + "进入方法run3等待..");
            c2.await();
            System.out.println("当前线程：" +Thread.currentThread().getName() + "方法run3继续..");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void run4(){
        try {
            lock.lock();
            System.out.println("当前线程：" +Thread.currentThread().getName() + "run4唤醒..");
            c1.signalAll(); //object.notifyAll()
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void run5(){
        try {
            lock.lock();
            System.out.println("当前线程：" +Thread.currentThread().getName() + "run5唤醒..");
            c2.signal();	//object.notify()
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
