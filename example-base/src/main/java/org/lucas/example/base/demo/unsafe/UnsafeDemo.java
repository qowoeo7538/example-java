package org.lucas.example.base.demo.unsafe;

import org.junit.Test;
import org.lucas.example.base.demo.unsafe.impl.Value;
import org.lucas.util.UnsafeUtils;
import sun.misc.Unsafe;

/**
 * @Author: shaw
 * @Date: 2019/5/17 15:09
 */
public class UnsafeDemo {

    private static final Unsafe THE_UNSAFE = UnsafeUtils.getUnsafe();

    /**
     * 通过 {@code Unsafe} 修改对象值,该方法并不能保证修改
     * 值被其它线程立马发现,只有在 field 被 {@code volatile} 修饰才使用
     */
    @Test
    public void putOrderedLong() throws NoSuchFieldException {
        final Value value = new Value();
        System.out.println("前:" + value.value);
        // 获取属性的偏移量
        long valueOffset = THE_UNSAFE.objectFieldOffset(Value.class.getDeclaredField("value"));
        // var1: 修改属性的对象 var2: 属性偏移量 var3: 修改的值
        THE_UNSAFE.putOrderedLong(value, valueOffset, 10);
        System.out.println("后:" + value.value);
    }
}
