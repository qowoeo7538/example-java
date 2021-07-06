package org.lucas.example.foundation.basic.demo.struct.concurrent.list;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 替代 ArrayList、Vector (有序可重复的)
 * CopyOnWriteArraySet 替代 LinkedHashSet (有序不重复)
 * CopyOnWriteArrayList.add 与 CopyOnWriteArrayList.addIfAbsent 的区别
 * CopyOnWriteArraySet 是借助 addIfAbsent方法实现的,由于需要去重,所以性能低于CopyOnWriteArrayList
 */
public class CopyOnWriteArrayListDemo {
    /**
     * 如果元素不存在则添加，CopyOnWriteArraySet则是利用此函数实现的。
     */
    @Test
    public void demoAddIfAbsent() {
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
