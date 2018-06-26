package org.shaw.struct.queue.block;

import java.util.LinkedList;

/**
 * @create: 2018-01-12
 * @description:
 */
public class LinkedListDemo {
    public static void main(String[] args) {
        pollTest();
        System.out.println("=========================");
        popTest();
    }

    public static void popTest() {
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

    public static void pollTest() {
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
