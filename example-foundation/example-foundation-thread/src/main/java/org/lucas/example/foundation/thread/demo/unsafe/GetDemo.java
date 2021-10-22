package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

class GetDemo {

    private static final Unsafe THE_UNSAFE = UnsafeUtils.getUnsafe();

    /**
     * 获取对象数据
     */
    @Test
    void demoGetInt() throws NoSuchFieldException {
        final ValueDTO valueDTO = new ValueDTO();
        valueDTO.value = 11;

        long offset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("value"));

        int value = THE_UNSAFE.getInt(valueDTO, offset);
        System.out.println(value);
    }

    /**
     * 获取静态字段
     */
    @Test
    void demoStaticFieldGetInt() throws NoSuchFieldException {
        ValueDTO.staticValue = 11L;

        // 获取静态属性
        long offset = THE_UNSAFE.staticFieldOffset(ValueDTO.class.getDeclaredField("staticValue"));
        long value = THE_UNSAFE.getLong(ValueDTO.class, offset);
        System.out.println(value);
    }

    @Test
    void demoGetIntVolatile() throws NoSuchFieldException {
        final ValueDTO value = new ValueDTO();
        value.volatileValue = 11;

        // 获取 volatileValue 属性的内存偏移地址
        long offset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("volatileValue"));

        // 获取volatile age的值
        System.out.println("value:" + THE_UNSAFE.getIntVolatile(value, offset));
    }

}
