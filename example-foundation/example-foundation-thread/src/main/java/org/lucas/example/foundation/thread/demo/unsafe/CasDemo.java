package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ArrayValueDTO;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

import java.util.Arrays;

class CasDemo {

    private static final Unsafe THE_UNSAFE = UnsafeUtils.getUnsafe();

    @Test
    void demoCompareAndSwapInt() throws NoSuchFieldException {
        final ValueDTO valueDTO = new ValueDTO();
        valueDTO.value = 11;

        long offset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("value"));

        //验证CAS方法
        boolean result = THE_UNSAFE.compareAndSwapInt(valueDTO, offset, 10, 50);
        System.out.println(valueDTO.value + "," + result);

        //验证CAS方法
        result = THE_UNSAFE.compareAndSwapInt(valueDTO, offset, 11, 100);
        System.out.println(valueDTO.value + "," + result);
    }

    @Test
    void demoCompareAndSwapObject() throws NoSuchFieldException {
        ArrayValueDTO arrayValue = new ArrayValueDTO();
        long[] local = new long[]{3, 4, 5, 6};
        arrayValue.values = local;

        // 1. 获取 values 字段偏移地址
        long valueOffset = THE_UNSAFE.objectFieldOffset(ArrayValueDTO.class.getDeclaredField("values"));

        // 2. 设置数组
        THE_UNSAFE.compareAndSwapObject(arrayValue, valueOffset, local, new long[]{9, 8, 7, 6});
        System.out.println(Arrays.toString(arrayValue.values));

        // 3. 设置失败
        THE_UNSAFE.compareAndSwapObject(arrayValue, valueOffset, local, new long[]{3, 4, 5, 6});
        System.out.println(Arrays.toString(arrayValue.values));
    }

    /**
     * 数组属性CAS
     */
    @Test
    void demoArrayCompareAndSwap() {
        long[] array = new long[]{1, 1, 1, 1};

        // 数组第一个元素的地址偏移量
        int offset = THE_UNSAFE.arrayBaseOffset(long[].class);
        // 数组中一个元素的大小
        int scale = THE_UNSAFE.arrayIndexScale(long[].class);

        //对数组的某个元素采用cas方法
        boolean result = THE_UNSAFE.compareAndSwapLong(array, (long) offset + scale * 3, 1, 9);
        System.out.println(Arrays.toString(array) + "," + result);

        result = THE_UNSAFE.compareAndSwapLong(array, (long) offset + scale * 3, 1, 9);
        System.out.println(Arrays.toString(array) + "," + result);
    }

    /**
     * 静态字段
     */
    @Test
    void demoStaticFieldGetAndAddLong() throws NoSuchFieldException {
        ValueDTO.staticValue = 11L;

        // 获取静态属性
        long offset = THE_UNSAFE.staticFieldOffset(ValueDTO.class.getDeclaredField("staticValue"));
        boolean result = THE_UNSAFE.compareAndSwapLong(ValueDTO.class, offset, 11,22);
        System.out.println(result + "," + ValueDTO.staticValue);
    }

}
