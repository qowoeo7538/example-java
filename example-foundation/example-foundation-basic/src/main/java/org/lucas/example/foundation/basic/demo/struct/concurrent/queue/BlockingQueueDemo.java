package org.lucas.example.foundation.basic.demo.struct.concurrent.queue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * BlockingQueue的实现类之LinkedBlockingDeque
 * 基于链表的阻塞队列,内部维护一个链表存储缓存数据
 * 内部采用读写分离的锁机制,所以支持写入和读取的并发操作
 * 创建时可指定长度也可以不指定，不指定时代表无界队列
 * 不允许null值
 * ---------------------------------------------------
 * offer 如果队列已经满了,则不阻塞，不抛出异常
 * offer 可设置最大阻塞时间,2秒,如果队列还是满的,则不阻塞，不抛出异常
 * add 如果队列满了，则不阻塞，直接抛出异常
 * put 如果队列满了,则永远阻塞, 不抛出异常
 * ---------------------------------------------------
 * peek 读取头元素不移除，队列为空,返回null,不阻塞, 不抛异常
 * poll 读取头元素并移除，队列为空,返回null,不阻塞, 不抛异常
 * poll 可指定阻塞时间,2秒,如果队列依然为空,则返回null,不抛异常
 * take 读取头元素并移除,如果队列为空,则永远阻塞,不抛出异常
 * drainTo 取出queue中指定个数（或全部）的元素放入list中,并移除，当队列为空时不抛出异常
 */
public class BlockingQueueDemo {

    @Test
    public void demoAdd() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        queue.add(1);
        queue.offer(2);
        try {
            //不允许添加null元素
            queue.add(null);
        } catch (Exception e1) {
            System.out.println("queue.add(null)异常" + e1.getMessage());
        }
        try {
            queue.put(3);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        try {
            // 如果队列满了,则抛出异常
            queue.add(4);
        } catch (Exception e1) {
            System.out.println("queue.add(4)异常" + e1.getMessage());
        }
        System.out.println("1>>" + queue);
        // 如果队列已经满了,则不阻塞，不抛出异常
        queue.offer(4);
        System.out.println("queue.offer(4)>>" + queue);
        try {
            // 可设置最大阻塞时间,5秒,如果队列还是满的,则不阻塞，不抛出异常
            queue.offer(6, 5, TimeUnit.SECONDS);
            System.out.println("queue.offer(6, 5, TimeUnit.SECONDS)>>" + queue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            // 如果队列满了,则永远阻塞, 不抛出异常
            queue.put(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(">>" + queue);
    }

    @Test
    public void demoGet() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(2);
        queue.add(1);
        queue.add(2);

        // 读取头元素不移除
        System.out.println("1>>" + queue.peek());
        System.out.println(queue);

        // 读取头元素，并移除
        System.out.println("2>>" + queue.poll());
        System.out.println("3>>" + queue);

        try {
            // 获取头元素,并移除数据
            System.out.println("4>>" + queue.take());
            System.out.println("5>>" + queue);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        // 队列为空,返回null,不阻塞, 不抛异常
        System.out.println("6>>" + queue.peek());

        // 队列为空,返回null,不阻塞, 不抛异常
        System.out.println("7>>" + queue.poll());

        try {
            // 可指定阻塞时间,2秒,如果队列依然为空,则返回null,不抛异常
            System.out.println("8>>" + queue.poll(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            // 如果队列为空,则永远阻塞,不抛出异常
            queue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("9>>over");
    }

    @Test
    public static void demoTake2() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);

        ArrayList<Integer> list = new ArrayList<Integer>();
        // 英文 drain 喝光，喝干; 使（精力、金钱等）耗尽; 使流出; 排掉水;
        // 取出queue中指定个数的元素放入list中,并移除
        queue.drainTo(list, 2);
        System.out.println(list);
        System.out.println(queue);
    }

    @Test
    public void demoTake3() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);

        ArrayList<Integer> list = new ArrayList<Integer>();
        // 取出queue中的全部元素放入list中,并移除
        queue.drainTo(list);
        System.out.println("1>>" + list);
        System.out.println("2>>" + queue);

        ArrayList<Integer> list1 = new ArrayList<Integer>();
        // 当队列为空时不抛出异常
        queue.drainTo(list1);
        System.out.println("3>>" + list1);
        System.out.println("4>>" + queue);
    }

}
