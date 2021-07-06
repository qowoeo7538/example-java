package org.lucas.example.foundation.basic.demo.struct.concurrent.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LinkedQueueDemo {

    public void demo(){
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();

        queue.add(1);
        //add方法实际调用了offer方法
        queue.add(2);

        //英文：offer 提供，给予; 提出，提议; 出价，开价; 表示愿意;
        //offer方法与add没有区别
        queue.offer(3);
        queue.offer(4);

        //不允许添加null元素
        //queue.add(null);
        System.out.println(queue);

        //英文：peek 偷看，窥视; 一瞥，看一眼
        //读取头元素,但是不移除
        System.out.println("[1]peek="+queue.peek());
        //peek方法不会导致size改变
        System.out.println("[2]size="+queue.size());

        //英文: poll 得到（一定数目的）选票; 对…进行调查; 修剪; 修剪;
        //读取头元素，并且移除
        System.out.println("[3]poll="+queue.poll());
        //poll方法导致size改变
        System.out.println("[4]size="+queue.size());

        System.out.println("[5]poll="+queue.poll());
        System.out.println("[6]poll="+queue.poll());
        System.out.println("[7]poll="+queue.poll());
        System.out.println("[8]size="+queue.size());

        //队列为空, 读取头元素，返回null
        System.out.println("peek="+queue.peek());
        //队列为空, 读取头元素并移除, 返回null
        System.out.println("pool="+queue.poll());
    }

}
