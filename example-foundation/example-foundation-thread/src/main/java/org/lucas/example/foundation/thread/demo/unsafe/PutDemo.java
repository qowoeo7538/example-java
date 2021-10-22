package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ArrayValueDTO;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.lucas.example.common.pojo.entity.Student;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

import java.util.Arrays;

/**
 * @Author: shaw
 * @Date: 2019/5/17 15:09
 */
class PutDemo {

    private static final Unsafe THE_UNSAFE = UnsafeUtils.getUnsafe();

    /**
     * 通过 {@code Unsafe} putOrderedXXX 修改对象值,在此写入与任何以前的存储之间设置 Store/Store 屏障，
     * 但不保证写入的值后续会立即被其它线程发现。
     * <p>
     * 优化被 {@code volatile} 修饰字段的不必要的内存屏障。
     */
    @Test
    void demoOrderedLong() throws NoSuchFieldException {
        final ValueDTO value = new ValueDTO();
        System.out.println("前:" + value.value);
        // 获取属性的偏移量
        long offset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("value"));
        // var1: 修改属性的对象 var2: 属性偏移量 var3: 修改的值
        // 插入StoreStore内存屏障。
        THE_UNSAFE.putOrderedLong(value, offset, 10);
        System.out.println("后:" + value.value);
    }

    @Test
    void demoPutInt() throws NoSuchFieldException {
        Student<String> student = new Student<>();

        //获取age属性的内存偏移地址
        long offset = THE_UNSAFE.objectFieldOffset(Student.class.getDeclaredField("age"));
        //设置age的值为11
        THE_UNSAFE.putInt(student, offset, 11);
        //输出结果
        System.out.println(student.getAge());
    }

    /**
     * 对象设置
     */
    @Test
    void demoPutObject() throws NoSuchFieldException {
        // 1. 获取 values 字段偏移地址
        long valueOffset = THE_UNSAFE.objectFieldOffset(ArrayValueDTO.class.getDeclaredField("values"));

        // 2. 设置数组
        ArrayValueDTO arrayValue = new ArrayValueDTO();
        long[] local = new long[]{3, 4, 5, 6};
        THE_UNSAFE.putObject(arrayValue, valueOffset, local);
        System.out.println(Arrays.toString(arrayValue.values));
    }

    /**
     * 数组对象设置
     */
    @Test
    void demoArrayPutObject() {
        // 数组第一个元素的地址偏移量
        int offset = THE_UNSAFE.arrayBaseOffset(long[].class);
        // 数组中一个元素的大小
        int scale = THE_UNSAFE.arrayIndexScale(long[].class);

        // 操作对象数组属性
        {
            ArrayValueDTO arrayValue = new ArrayValueDTO();
            arrayValue.values = new long[]{1, 2, 3};

            System.out.println("offset: " + offset);
            System.out.println("scale: " + scale);

            // 设置数组中第1个元素的值 = 第一个元素的地址偏移量 + (数组中一个元素的大小 * (设置的第几个元素 - 1))
            THE_UNSAFE.putLong(arrayValue.values, (long) offset + scale * 0, 9);
            System.out.println("元素1: " + arrayValue.values[0]);

            //设置数组中第2个元素的值
            THE_UNSAFE.putLong(arrayValue.values, (long) offset + scale * 1, 8);
            System.out.println("元素2: " + arrayValue.values[1]);

            //设置数组中第3个元素的值
            THE_UNSAFE.putLong(arrayValue.values, (long) offset + scale * 2, 7);
            System.out.println("元素3: " + arrayValue.values[2]);

            System.out.println(Arrays.toString(arrayValue.values));
        }
        // 操作局部变量数组
        {
            long[] array = new long[]{1, 1, 1};

            THE_UNSAFE.putLong(array, (long) offset + scale * 0, 9);
            System.out.println("操作局部变量数组>>" + Arrays.toString(array));
            THE_UNSAFE.putLong(array, (long) offset + scale * 1, 9);
            System.out.println("操作局部变量数组>>" + Arrays.toString(array));
            THE_UNSAFE.putLong(array, (long) offset + scale * 2, 9);
            System.out.println("操作局部变量数组>>" + Arrays.toString(array));
        }
    }

    @Test
    void demoPutIntVolatile() throws NoSuchFieldException {
        final ValueDTO value = new ValueDTO();

        // 获取age属性的内存偏移地址
        long ageOffset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("volatileValue"));

        //设置volatile age的值为11
        THE_UNSAFE.putIntVolatile(value, ageOffset, 11);
        System.out.println("value:" + value.volatileValue);
    }

}
