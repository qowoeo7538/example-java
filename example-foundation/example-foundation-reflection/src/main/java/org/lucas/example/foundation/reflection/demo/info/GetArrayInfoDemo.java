package org.lucas.example.foundation.reflection.demo.info;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

public class GetArrayInfoDemo {

    @Test
    public void arrayInfo(){
        //创建数组对象;
        String[] strings = (String[]) Array.newInstance(String.class, 3);
        Array.set(strings, 2, "第三个数");
        Array.set(strings, 1, "第二个数");
        Array.set(strings, 0, "第一个数");
        System.out.println("是否是数组：" + strings.getClass().isArray());
        System.out.println("数组长度：" + Array.getLength(strings));
        System.out.println("数组的元素类型：" + strings.getClass().getComponentType());
        System.out.println(Array.get(strings, 0));
        System.out.println(Array.get(strings, 1));
        System.out.println(Array.get(strings, 2));
    }

}
