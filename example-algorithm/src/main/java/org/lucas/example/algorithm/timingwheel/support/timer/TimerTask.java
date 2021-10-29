package org.lucas.example.algorithm.timingwheel.support.timer;

/**
 * 任务
 */
public class TimerTask {

    /**
     * 延迟时间
     */
    private final long delayMs;

    /**
     * 任务
     */
    private final Runnable task;

    /**
     * 时间槽
     */
    protected TimerTaskList timerTaskList;

    /**
     * 上一个节点
     */
    protected TimerTask pre;

    /**
     * 下一个节点
     */
    protected TimerTask next;

    public TimerTask(long delayMs, Runnable task) {
        this.delayMs = System.currentTimeMillis() + delayMs;
        this.task = task;
        this.timerTaskList = null;
        this.next = null;
        this.pre = null;
    }

    public Runnable getTask() {
        return task;
    }

    public long getDelayMs() {
        return delayMs;
    }

}
