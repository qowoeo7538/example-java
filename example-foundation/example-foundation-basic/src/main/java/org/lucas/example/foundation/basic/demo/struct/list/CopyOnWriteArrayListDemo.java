package org.lucas.example.foundation.basic.demo.struct.list;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {
    /**
     * 如果元素不存在则添加，CopyOnWriteArraySet则是利用此函数实现的。
     */
    @Test
    public void demoAddIfAbsent() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.addIfAbsent("张三");
        list.addIfAbsent("张三");
        System.out.println(list);
    }
}
