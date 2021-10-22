package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

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
     * 获取一个Java对象o中，获取偏移地址为offset的属性的值，此方法可以突破修饰符的抑制，也就是无视private、protected和default修饰符。
     * 类似的方法有getInt、getDouble等等。
     */
    @Test
    void demoGetObject() {

    }

    /**
     * 返回给定的静态属性在它的类的存储分配中的位置(偏移地址)。不要在这个偏移量上执行任何类型的算术运算，它只是一个被传递给不安全的堆内存访问器的cookie。
     * 注意：这个方法仅仅针对静态属性，使用在非静态属性上会抛异常。
     */
    @Test
    void demoStaticFieldGetInt() throws NoSuchFieldException {
        ValueDTO.staticValue = 11L;

        // 获取静态属性
        long offset = THE_UNSAFE.staticFieldOffset(ValueDTO.class.getDeclaredField("staticValue"));
        long value = THE_UNSAFE.getLong(ValueDTO.class, offset);
        System.out.println(value);
    }

    /**
     * 返回给定的静态属性的位置，配合staticFieldOffset方法使用。实际上，这个方法返回值就是静态属性所在的Class对象的一个内存快照。
     * 此方法返回的Object有可能为null，它只是一个'cookie'而不是真实的对象，不要直接使用的它的实例中的获取属性和设置属性的方法，
     * 它的作用只是方便调用上面提到的像getInt(Object,long)等等的任意方法。
     */
    @Test
    void demoStaticFieldBase() throws NoSuchFieldException {
        ValueDTO.staticValue = 11L;

        Field name = ValueDTO.class.getField("staticValue");
        Object obj = THE_UNSAFE.staticFieldBase(name);
        long offset = THE_UNSAFE.staticFieldOffset(name);

        //注意，上面的Field实例是通过Class获取的，但是下面的获取静态属性的值没有依赖到Class
        Object value = THE_UNSAFE.getObject(obj, offset);
        System.out.println(value);
    }

    @Test
    void demoGetIntVolatile() throws NoSuchFieldException {
        final ValueDTO value = new ValueDTO();
        value.volatileValue = 11;

        // 返回给定的非静态属性在它的类的存储分配中的位置(偏移地址)。不要在这个偏移量上执行任何类型的算术运算，它只是一个被传递给不安全的堆内存访问器的cookie。
        // 注意：这个方法仅仅针对非静态属性，使用在静态属性上会抛异常。
        long offset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("volatileValue"));

        // 获取volatile age的值
        System.out.println("value:" + THE_UNSAFE.getIntVolatile(value, offset));
    }

    /**
     * 附加了'volatile'加载语义，也就是强制从主存中获取属性值。类似的方法有getIntVolatile、getDoubleVolatile等等。
     * 这个方法要求被使用的属性被volatile修饰，否则功能和getObject方法相同。
     */
    @Test
    void demoGetObjectVolatile() {

    }

}
