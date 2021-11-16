package org.lucas.example.foundation.basic.demo.struct.concurrent.list;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWriteArrayList 替代 ArrayList、Vector (有序可重复的)
 * CopyOnWriteArraySet 替代 LinkedHashSet (有序不重复)
 * CopyOnWriteArrayList.add 与 CopyOnWriteArrayList.addIfAbsent 的区别
 * CopyOnWriteArraySet 是借助 addIfAbsent方法实现的,由于需要去重,所以性能低于CopyOnWriteArrayList
 */
class CopyOnWriteArrayListDemo {

    /**
     * 用一个小例子来说明快照的产生
     */
    @Test
    void demo() {
        Object[] array = new Object[]{1, 2, 3};
        Object[] snapshot = array;
        Object[] newArray = new Object[]{1, 2, 3, 4};
        array = newArray;
        System.out.println("array:" + Arrays.toString(array));
        System.out.println("snapshot:" + Arrays.toString(snapshot));
    }

    /**
     * CopyOnWriteArraySet 的迭代器也为弱一致性迭代器
     */
    @Test
    void demoWeakConsistency() throws InterruptedException {
        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();
        set.add(1);
        set.add(2);
        set.add(3);

        Iterator<Integer> iterator = set.iterator();

        Thread td = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            set.add(4);
            set.add(5);
        });

        td.start();
        td.join();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("---------------------");

        iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 如果元素不存在则添加，CopyOnWriteArraySet则是利用此函数实现的。
     */
    @Test
    void demoAddIfAbsent() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list);
        list.add(1); //无论元素是否已经存在都添加
        System.out.println(list);
        //如果元素不存在则不用添加,CopyOnWriteArraySet就是利用此函数实现的
        //英文：Absent adj.	缺席的，不在场的; 缺少的，缺乏的; 不在意的，茫然的;
        list.addIfAbsent(2);
        System.out.println(list);
    }
}
