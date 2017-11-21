package org.shaw.util.thread.impl;

/**
 * 使用该类实现线程任务,保证线程池关闭时等待该线程正常关闭
 * <p>
 * 普通方式,如果线程处于阻塞或者睡眠状态,将导致线程池无法正
 * 常关闭释放资源,或者睡眠线程池立即唤醒.
 */
public class SecurityTask implements Runnable {

    private final Runnable target;

    private ThrottleSupport throttleSupport;

    public SecurityTask(Runnable target, ThrottleSupport throttleSupport) {
        this.target = target;
        this.throttleSupport = throttleSupport;
    }

    /**
     * 结束后通过{@link ThrottleSupport#afterAccess()}减少运行线程
     */
    @Override
    public void run() {
        try {
            target.run();
        } finally {
            throttleSupport.afterAccess();
        }
    }

}
