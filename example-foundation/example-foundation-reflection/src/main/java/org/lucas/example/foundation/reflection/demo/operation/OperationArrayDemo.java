package org.lucas.example.foundation.reflection.demo.operation;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

public class OperationArrayDemo {

    /**
     * 操作多维数组
     */
    @Test
    public void operationMultiArray() {
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
