package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

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

}
