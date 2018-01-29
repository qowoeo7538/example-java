package org.shaw.structure.set;

import java.util.LinkedHashSet;

/**
 * @create: 2018-01-29
 * @description:
 */
public class LinkedHashSetDemo {
    public static void main(String[] args) {
        strShow();
    }

    /**
     * AbstractCollection 对 toString 方法进行的重写
     */
    private static void strShow() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add("张三");
        set.add("李四");
        set.add("王老五");
        System.out.println("输出：" + set);
    }
}
