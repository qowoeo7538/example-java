package org.lucas.demo.notify;

import org.junit.Test;
import org.lucas.core.task.ExampleThreadExecutor;
import org.lucas.demo.notify.impl.DoSomething;
import org.lucas.demo.notify.impl.NotifyAllTest;

/**
 * synchronized
 * <p>
 * notifyAll()唤醒的睡眠线程,只有当执行完synchronized代码块才会继续唤醒其他线程
 */
public class WaitNotifyDemo {

    /**
     * spring源码测试
     * <p>
     * notify();
     */
    @Test
    public void notifyTest() {
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(new DoSomething("线程" + i));
        }
        ExampleThreadExecutor.destroy();
    }

    /**
     * spring源码测试
     * <p>
     * notifyAll();
     */
    @Test
    public void notifyAllTest() {
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(new NotifyAllTest("线程" + i));
        }
        ExampleThreadExecutor.destroy();
    }
}
