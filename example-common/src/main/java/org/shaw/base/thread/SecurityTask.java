package org.shaw.base.thread;

import org.shaw.util.thread.DefaultThreadFactory;

/**
 * 使用该类实现线程任务,保证线程池关闭时等待该线程正常关闭
 * <p>
 * 普通方式,如果线程处于阻塞或者睡眠状态,将导致线程池无法正
 * 常关闭释放资源,或者睡眠线程池立即唤醒.
 */
public abstract class SecurityTask implements Runnable {

    @Override
    public void run() {
        runTask();
        DefaultThreadFactory.latchDecrement();
    }

    /**
     * 实际运行任务
     */
    protected abstract void runTask();
}
