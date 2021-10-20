package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.lucas.example.common.pojo.entity.Student;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

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

}
