package org.lucas.example.foundation.thread.kata.safe;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.thread.kata.safe.impl.Value;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @Author: shaw
 * @Date: 2019/5/17 15:09
 */
public class UnsafeDemo {

    private static final Unsafe THE_UNSAFE = UnsafeUtils.getUnsafe();

    /**
     * 通过 {@code Unsafe} putOrderedXXX 修改对象值,在此写入与任何以前的存储之间设置 Store/Store 屏障，
     * 但不保证写入的值后续会立即被其它线程发现。
     * <p>
     * 优化被 {@code volatile} 修饰字段的不必要的内存屏障。
     */
    @Test
    public void putOrderedLong() throws NoSuchFieldException {
        final Value value = new Value();
        System.out.println("前:" + value.value);
        // 获取属性的偏移量
        long valueOffset = THE_UNSAFE.objectFieldOffset(Value.class.getDeclaredField("value"));
        // var1: 修改属性的对象 var2: 属性偏移量 var3: 修改的值
        // 插入StoreStore内存屏障。
        THE_UNSAFE.putOrderedLong(value, valueOffset, 10);
        System.out.println("后:" + value.value);
    }

    /**
     * 原子性修改对象属性值,变量需要保证可见性
     */
    @Test
    public void fieldUpdater() {
        AtomicReferenceFieldUpdater<Value, String> updater = AtomicReferenceFieldUpdater.newUpdater(Value.class, String.class, "name");
        Value value = new Value();
        System.out.println(updater.get(value));
        while (!updater.compareAndSet(value, value.name, "test")) {
            System.out.println(updater.get(value));
        }
    }
}
