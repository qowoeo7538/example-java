package org.shaw.enumerate;

import org.junit.Test;

/**
 * Created by joy on 17-6-11.
 */
public class EnumDemo {

    /**
     * 获取枚举信息
     */
    @Test
    public void getInfo() {
        SingletonDemo spring = SingletonDemo.spring;
        System.out.println(spring);

        System.out.println("=================================");
        System.out.println("枚举元素个数： " + SingletonDemo.values().length);
        for (SingletonDemo e : SingletonDemo.values()) {
            System.out.println(e);
        }
        System.out.println("=================================");
        //枚举已经实现compareTo接口
        System.out.println(SingletonDemo.spring.compareTo(SingletonDemo.winter));
        System.out.println(SingletonDemo.getBest());
    }

}

