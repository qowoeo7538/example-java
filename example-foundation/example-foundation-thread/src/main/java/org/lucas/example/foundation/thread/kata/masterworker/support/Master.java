package org.lucas.example.foundation.thread.kata.masterworker.support;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Master
 * 1. 接收Client提交过来的任务
 * 2. 创建Worker
 * 3. 运行Worker
 * 4. 汇总Task的处理结果
 */
public class Master {

    /**
     * 存放任务的队列
     */
    private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    /**
     * 存放Worker的Map
     */
    private HashMap<String, Thread> workers = new HashMap<>();

    /**
     * 存放每一个Worker的处理结果
     */
    private ConcurrentHashMap<String, Object> taskResultMap = new ConcurrentHashMap<>();

    /**
     * 构造函数
     *
     * @param worker      worker处理类
     * @param workerCount worker的数量
     */
    public Master(Worker worker, int workerCount) {
        worker.setWorkQueue(this.taskQueue);
        worker.setTaskResultMap(this.taskResultMap);
        //将Worker放入容器
        for (int i = 0; i < workerCount; i++) {
            this.workers.put(Integer.toString(i), new Thread(worker));
        }
    }

    /**
     * 需要一个提交任务,就是讲任务放入队列中
     */
    public void submit(Task task) {
        this.taskQueue.add(task);
    }

    /**
     * 执行方,启动所有的worker方法去执行任务
     */
    public void execute() {
        for (Map.Entry<String, Thread> me : workers.entrySet()) {
            me.getValue().start();
        }
    }

    /**
     * 判断是否运行结束的方法
     */
    public boolean isComplete() {
        for (Map.Entry<String, Thread> me : workers.entrySet()) {
            if (me.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 汇总每个任务的结果
     */
    public int getResult() {
        int priceResult = 0;
        for (Map.Entry<String, Object> me : taskResultMap.entrySet()) {
            priceResult += (Integer) me.getValue();
        }
        return priceResult;
    }

}
