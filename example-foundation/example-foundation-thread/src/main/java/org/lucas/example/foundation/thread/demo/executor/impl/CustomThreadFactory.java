package org.lucas.example.foundation.thread.demo.executor.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * @create: 2017-11-08
 * @description: 自定义线程工厂
 */
public class CustomThreadFactory implements ThreadFactory {

    /**
     * 线程次数
     */
    private int counter;

    /**
     * 线程名
     */
    private String name;

    /**
     * 线程状态信息
     */
    private List<String> stats;

    public CustomThreadFactory(String name) {
        counter = 1;
        this.name = name;
        stats = new ArrayList<>();
    }

    /**
     * 线程创建处理
     *
     * @param runnable Runnable对象
     * @return Thread
     */
    @Override
    public Thread newThread(Runnable runnable) {
        //创建线程
        Thread t = new Thread(runnable, name + "-Thread_" + counter);
        counter++;
        stats.add(String.format("Created thread %d with name %s on %s \n", t.getId(), t.getName(), new Date()));
        return t;
    }

    /**
     * 获取线程状态
     *
     * @return String
     */
    public String getStats() {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> it = stats.iterator();
        while (it.hasNext()) {
            buffer.append(it.next());
        }
        return buffer.toString();
    }
}
