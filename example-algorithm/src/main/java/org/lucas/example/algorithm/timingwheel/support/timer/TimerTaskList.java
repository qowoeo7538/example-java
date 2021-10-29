package org.lucas.example.algorithm.timingwheel.support.timer;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class TimerTaskList implements Delayed {

    /**
     * 过期时间
     */
    private final AtomicLong expiration = new AtomicLong(-1L);

    /**
     * 根节点
     */
    private TimerTask root = new TimerTask(-1L, null);

    {
        root.pre = root;
        root.next = root;
    }

    /**
     * 新增任务
     */
    public void addTask(TimerTask timerTask) {
        synchronized (this) {
            if (timerTask.timerTaskList == null) {
                timerTask.timerTaskList = this;
                TimerTask tail = root.pre;
                timerTask.next = root;
                timerTask.pre = tail;
                tail.next = timerTask;
                root.pre = timerTask;
            }
        }
    }

    /**
     * 重新分配
     */
    public synchronized void flush(Consumer<TimerTask> flush) {
        TimerTask timerTask = root.next;
        while (!timerTask.equals(root)) {
            this.removeTask(timerTask);
            flush.accept(timerTask);
            timerTask = root.next;
        }
        expiration.set(-1L);
    }

    /**
     * 移除任务
     */
    public void removeTask(TimerTask timerTask) {
        synchronized (this) {
            if (timerTask.timerTaskList.equals(this)) {
                timerTask.next.pre = timerTask.pre;
                timerTask.pre.next = timerTask.next;
                timerTask.timerTaskList = null;
                timerTask.next = null;
                timerTask.pre = null;
            }
        }
    }

    /**
     * 设置过期时间
     */
    public boolean setExpiration(long expire) {
        return expiration.getAndSet(expire) != expire;
    }

    /**
     * 获取过期时间
     */
    public long getExpiration() {
        return expiration.get();
    }

    /**
     * 以给定的时间单位返回与此对象关联的剩余延迟.零或负值标识任务达到指定时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return Math.max(0, unit.convert(expiration.get() - System.currentTimeMillis(), TimeUnit.MILLISECONDS));
    }

    /**
     * 根据延时时间排序
     */
    @Override
    public int compareTo(Delayed o) {
        if (o instanceof TimerTaskList) {
            return Long.compare(expiration.get(), ((TimerTaskList) o).expiration.get());
        }
        return 0;
    }

}
