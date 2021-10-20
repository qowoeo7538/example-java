package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

class GetAndSetDemo {

    private static final Unsafe THE_UNSAFE = UnsafeUtils.getUnsafe();

    @Test
    void demoGetAndSetInt() throws NoSuchFieldException {
        final ValueDTO valueDTO = new ValueDTO();
        valueDTO.value = 11;

        long offset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("value"));

        int value = THE_UNSAFE.getAndSetInt(valueDTO, offset, 99);
        System.out.println(value + "," + valueDTO.value);
    }

}
