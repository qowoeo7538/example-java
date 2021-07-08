package org.lucas.example.foundation.basic.demo.struct.concurrent.queue;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.entity.Player;

import java.util.concurrent.DelayQueue;

public class DelayDemo {

    @Test
    public void demoDelay() {
        DelayQueue<Player> delayQueue = new DelayQueue<>();

        //用户登录,并设置退出时间
        login(delayQueue, new Player(1, "甲", 30000 + System.currentTimeMillis()));
        login(delayQueue, new Player(2, "乙", 20000 + System.currentTimeMillis()));
        login(delayQueue, new Player(3, "丙", 10000 + System.currentTimeMillis()));

        while (true) {
            //监控到时用户
            logout(delayQueue);
            //如果在线用户则退出
            if (onlineSize(delayQueue) == 0) {
                break;
            }
        }
    }

    /**
     * 登录游戏,加入队列
     */
    private void login(DelayQueue<Player> delayQueue, Player user) {
        delayQueue.add(user);
        System.out.println("用户(" + user.getId() + ")" + user.getName() + "已登录,预计下机时间为" + user.getEndTime());
    }

    //

    /**
     * 退出游戏,移除队列
     */
    private void logout(DelayQueue<Player> delayQueue) {
        try {
            System.out.println(delayQueue);
            Player user = delayQueue.take();
            //User user = delayQueue.poll(); //不能使用poll方法,因为没有阻塞功能
            System.out.println("用户(" + user.getId() + ")" + user.getName() + "到时自动退出,时间为" + System.currentTimeMillis() + "...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前在线人数
     */
    private int onlineSize(DelayQueue<Player> delayQueue) {
        return delayQueue.size();
    }

}
