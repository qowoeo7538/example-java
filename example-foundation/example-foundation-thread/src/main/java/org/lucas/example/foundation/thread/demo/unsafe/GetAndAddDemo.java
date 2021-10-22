package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

class GetAndAddDemo {

    private static final Unsafe THE_UNSAFE = UnsafeUtils.getUnsafe();

    @Test
    void demoGetAndAddInt() throws NoSuchFieldException {
        final ValueDTO valueDTO = new ValueDTO();
        valueDTO.value = 11;

        // 返回给定的非静态属性在它的类的存储分配中的位置(偏移地址)。不要在这个偏移量上执行任何类型的算术运算，它只是一个被传递给不安全的堆内存访问器的cookie。
        // 注意：这个方法仅仅针对非静态属性，使用在静态属性上会抛异常。
        long offset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("value"));

        int value = THE_UNSAFE.getAndAddInt(valueDTO, offset, 100);
        System.out.println(value + "," + valueDTO.value);
    }

    /**
     * 获取静态字段
     */
    @Test
    void demoStaticFieldGetAndAddLong() throws NoSuchFieldException {
        ValueDTO.staticValue = 11L;

        // 获取静态属性
        long offset = THE_UNSAFE.staticFieldOffset(ValueDTO.class.getDeclaredField("staticValue"));
        long value = THE_UNSAFE.getAndAddLong(ValueDTO.class, offset, 11);
        System.out.println(value + "," + ValueDTO.staticValue);
    }
}
