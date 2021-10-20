package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

class GetAndAdd {

    private static final Unsafe THE_UNSAFE = UnsafeUtils.getUnsafe();

    @Test
    void demoGetAndAddInt() throws NoSuchFieldException {
        final ValueDTO valueDTO = new ValueDTO();
        valueDTO.value = 11;

        long offset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("value"));

        int value = THE_UNSAFE.getAndAddInt(valueDTO, offset, 100);
        System.out.println(value + "," + valueDTO.value);
    }
}
