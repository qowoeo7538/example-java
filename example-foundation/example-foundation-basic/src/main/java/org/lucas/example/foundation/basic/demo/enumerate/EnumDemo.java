package org.lucas.example.foundation.basic.demo.enumerate;

import org.junit.jupiter.api.Test;

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
        System.out.print("枚举元素信息：");
        for (SingletonDemo e : SingletonDemo.values()) {
            System.out.print(e+" ");
        }
        System.out.println();
        System.out.println("=================================");
        //枚举已经实现compareTo接口
        System.out.println("比较指定枚举的顺序： " + SingletonDemo.spring.compareTo(SingletonDemo.winter));
        System.out.println(SingletonDemo.getBest());
    }

}

