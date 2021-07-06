package org.lucas.example.foundation.basic.demo.struct.unsafe.list;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

/**
 * 链表数据结构
 */
public class LinkedListDemo {

    /**
     * 从队首获取元素，同时获取的这个元素将从原队列删除,调用该方法会发生异常
     */
    @Test
    public void popTest() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println(list.pop());
        System.out.println(list.pop());
        System.out.println(list.pop());
    }

    /**
     * 返从队首获取元素，同时获取的这个元素将从原队列删除,调用该方法会返回null
     */
    @Test
    public void pollTest() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println(list.poll());
        System.out.println(list.poll());
        System.out.println(list.poll());
    }
}
