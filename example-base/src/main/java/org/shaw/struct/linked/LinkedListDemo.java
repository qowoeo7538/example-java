package org.shaw.struct.linked;

import org.junit.Test;

import java.util.LinkedList;

/**
 * 链表数据结构
 */
public class LinkedListDemo {

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
