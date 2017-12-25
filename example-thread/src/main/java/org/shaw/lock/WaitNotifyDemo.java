package org.shaw.lock;

import org.shaw.lock.impl.DoSomething;
import org.shaw.lock.impl.NotifyAllTest;

/**
 * synchronized
 * <p>
 * notifyAll()唤醒的睡眠线程,只有当执行完synchronized代码块才会继续唤醒其他线程
 */
public class WaitNotifyDemo {
    public static void main(String[] args) {
        notifyAllTest();
    }

    /**
     * spring源码测试
     * <p>
     * notify();
     */
    public static void concurrencyThrottleSupportTest() {
        for (int i = 0; i < 10; i++) {
            new Thread(new DoSomething("线程" + i)).start();
        }
    }

    /**
     * spring源码测试
     * <p>
     * notifyAll();
     */
    public static void notifyAllTest() {
        for (int i = 0; i < 10; i++) {
            new Thread(new NotifyAllTest("线程" + i)).start();
        }
    }
}
