package org.lucas.example.foundation.reflection.demo.operation.impl;

import java.util.List;

/**
 * @create: 2017-11-08
 * @description:
 */
public class ConstructorReflectClass {

    public String name = "wpc";

    public ConstructorReflectClass() {
        System.out.println("无参构造函数");
    }

    public ConstructorReflectClass(String name) {
        System.out.println("参数为name的构造函数");
    }

    public ConstructorReflectClass(String name, int pass) {
        System.out.println("参数为name,pass的构造函数");
    }

    /**
     * 私有的构造函数，通过反射的setAccessible(true)设置它可以被外部访问
     *
     * @param list 示例参数
     */
    private ConstructorReflectClass(List list) {
        System.out.println("参数为list集合的构造函数");
    }
}
