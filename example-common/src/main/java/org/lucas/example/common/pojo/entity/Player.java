package org.lucas.example.common.pojo.entity;

import org.lucas.example.common.pojo.DelayedEntity;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Player implements DelayedEntity {


    private int id;

    private String name;

    /**
     * 退出时间
     */
    private long endTime;

    public Player(int id, String name, long endTime) {
        this.id = id;
        this.name = name;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * 由于需要根据延时时间的长短,计算从数据中移除的顺序，所以需要实现compareTo方法计算优先级，类似优先级队列
     */
    @Override
    public int compareTo(Delayed o) {
        Player user = (Player) o;
        if (this.endTime > user.getEndTime()) {
            return 1;
        } else if (this.endTime < user.getEndTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 计算剩余延迟时间；零或负值指示延迟时间已经用尽
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return this.endTime - System.currentTimeMillis();
    }

}
