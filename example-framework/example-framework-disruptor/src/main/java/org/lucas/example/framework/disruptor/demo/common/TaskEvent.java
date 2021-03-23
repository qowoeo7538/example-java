package org.lucas.example.framework.disruptor.demo.common;

/**
 * @author TaoXi Plus Team
 * @since 0.0.1 on 2020-12-31
 */
public class TaskEvent<T> {

    /**
     * 具体的任务
     *
     * <p>当且仅当仅当 {@link #reset()}时可以为null
     */
    private T action;

    /**
     * 重置方法释放容器持有的任务数据
     *
     * <p>disruptor 会长期持有该类的异常，清空里面的action降低内存的消耗
     */
    public void reset() {
        this.setAction(null);
    }

    public T getAction() {
        return action;
    }

    public void setAction(T action) {
        this.action = action;
    }
}
