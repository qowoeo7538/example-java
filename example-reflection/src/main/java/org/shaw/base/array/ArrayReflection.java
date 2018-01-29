package org.shaw.base.array;

import java.lang.reflect.Array;

/**
 * 数组反射
 */
public class ArrayReflection {
    public static void main(String[] args) {
        single();
    }

    /**
     * 单维数组
     */
    private static void single() {
        //创建int数组对象;
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

    /**
     * 多维数组
     */
    private static void multi() {
        //为一个三维数组指定长度
        int[] dims = {5, 10, 15};
        Object array = Array.newInstance(String.class, dims);

        // 取出三维数组的第3行，为一个数组
        Object arrayObj = Array.get(array, 3);
        System.out.println("第三维度数组的元素类型：" + arrayObj.getClass().getComponentType());

        // 取出第3行的第5列，为一个数组
        arrayObj = Array.get(arrayObj, 5);

        // 访问第3行第5列的第10个元素，为其赋值37
        Array.set(arrayObj, 10, "3,5,10");

        // 动态数组和普通数组的转换：强行转换成对等的数组
        String[][][] arrayCast = (String[][][]) array;
        System.out.println(arrayCast[3][5][10]);
    }
}
