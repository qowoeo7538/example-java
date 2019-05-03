package org.lucas.demo.struct;

import org.junit.Test;

import java.util.AbstractCollection;
import java.util.LinkedHashSet;

/**
 * @create: 2018-01-29
 * @description:
 */
public class LinkedHashSetDemo {

    /**
     * AbstractCollection 对 toString 方法进行的重写
     *
     * @see AbstractCollection#toString()
     */
    @Test
    public void strToString(){
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add("张三");
        set.add("李四");
        set.add("王老五");
        System.out.println("输出：" + set);
    }

}
