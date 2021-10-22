package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.dto.ArrayValueDTO;
import org.lucas.example.common.pojo.dto.ValueDTO;
import org.lucas.example.common.pojo.entity.Student;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

import java.util.Arrays;

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
        // 返回给定的非静态属性在它的类的存储分配中的位置(偏移地址)。不要在这个偏移量上执行任何类型的算术运算，它只是一个被传递给不安全的堆内存访问器的cookie。
        // 注意：这个方法仅仅针对非静态属性，使用在静态属性上会抛异常。
        long offset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("value"));
        // var1: 修改属性的对象 var2: 属性偏移量 var3: 修改的值
        // 插入StoreStore内存屏障。
        THE_UNSAFE.putOrderedLong(value, offset, 10);
        System.out.println("后:" + value.value);
    }

    @Test
    void demoPutInt() throws NoSuchFieldException {
        Student<String> student = new Student<>();

        // 返回给定的非静态属性在它的类的存储分配中的位置(偏移地址)。不要在这个偏移量上执行任何类型的算术运算，它只是一个被传递给不安全的堆内存访问器的cookie。
        // 注意：这个方法仅仅针对非静态属性，使用在静态属性上会抛异常。
        long offset = THE_UNSAFE.objectFieldOffset(Student.class.getDeclaredField("age"));
        //设置age的值为11
        THE_UNSAFE.putInt(student, offset, 11);
        //输出结果
        System.out.println(student.getAge());
    }

    /**
     * 将引用值存储到给定的Java变量中。这里实际上是设置一个Java对象o中偏移地址为offset的属性的值为x，此方法可以突破修饰符的抑制，
     * 也就是无视private、protected和default修饰符。
     */
    @Test
    void demoPutObject() throws NoSuchFieldException {
        // 返回给定的非静态属性在它的类的存储分配中的位置(偏移地址)。不要在这个偏移量上执行任何类型的算术运算，它只是一个被传递给不安全的堆内存访问器的cookie。
        // 注意：这个方法仅仅针对非静态属性，使用在静态属性上会抛异常。
        long valueOffset = THE_UNSAFE.objectFieldOffset(ArrayValueDTO.class.getDeclaredField("values"));

        // 设置数组
        ArrayValueDTO arrayValue = new ArrayValueDTO();
        long[] local = new long[]{3, 4, 5, 6};
        THE_UNSAFE.putObject(arrayValue, valueOffset, local);
        System.out.println(Arrays.toString(arrayValue.values));
    }

    /**
     * 数组对象设置
     */
    @Test
    void demoArrayPutObject() {
        // 返回数组类型的第一个元素的偏移地址(基础偏移地址)。如果arrayIndexScale方法返回的比例因子不为0，可以结合基础偏移地址和比例因子访问数组的所有元素。
        int offset = THE_UNSAFE.arrayBaseOffset(long[].class);

        // 返回数组类型的比例因子(其实就是数据中元素偏移地址的增量，因为数组中的元素的地址是连续的)。
        // 此方法不适用于数组类型为"narrow"类型的数组，"narrow"类型的数组类型使用此方法会返回0(这里narrow应该是狭义的)。
        // Unsafe中已经初始化了很多类似的常量如ARRAY_BOOLEAN_INDEX_SCALE等。
        int scale = THE_UNSAFE.arrayIndexScale(long[].class);

        // 操作对象数组属性
        {
            ArrayValueDTO arrayValue = new ArrayValueDTO();
            arrayValue.values = new long[]{1, 2, 3};

            System.out.println("offset: " + offset);
            System.out.println("scale: " + scale);

            // 设置数组中第1个元素的值 = 第一个元素的地址偏移量 + (数组中一个元素的大小 * (设置的第几个元素 - 1))
            THE_UNSAFE.putLong(arrayValue.values, (long) offset + scale * 0, 9);
            System.out.println("元素1: " + arrayValue.values[0]);

            //设置数组中第2个元素的值
            THE_UNSAFE.putLong(arrayValue.values, (long) offset + scale * 1, 8);
            System.out.println("元素2: " + arrayValue.values[1]);

            //设置数组中第3个元素的值
            THE_UNSAFE.putLong(arrayValue.values, (long) offset + scale * 2, 7);
            System.out.println("元素3: " + arrayValue.values[2]);

            System.out.println(Arrays.toString(arrayValue.values));
        }
        // 操作局部变量数组
        {
            long[] array = new long[]{1, 1, 1};

            THE_UNSAFE.putLong(array, (long) offset + scale * 0, 9);
            System.out.println("操作局部变量数组>>" + Arrays.toString(array));
            THE_UNSAFE.putLong(array, (long) offset + scale * 1, 9);
            System.out.println("操作局部变量数组>>" + Arrays.toString(array));
            THE_UNSAFE.putLong(array, (long) offset + scale * 2, 9);
            System.out.println("操作局部变量数组>>" + Arrays.toString(array));
        }
    }

    @Test
    void demoPutIntVolatile() throws NoSuchFieldException {
        final ValueDTO value = new ValueDTO();

        // 返回给定的非静态属性在它的类的存储分配中的位置(偏移地址)。不要在这个偏移量上执行任何类型的算术运算，它只是一个被传递给不安全的堆内存访问器的cookie。
        // 注意：这个方法仅仅针对非静态属性，使用在静态属性上会抛异常。
        long ageOffset = THE_UNSAFE.objectFieldOffset(ValueDTO.class.getDeclaredField("volatileValue"));

        //设置volatile age的值为11
        THE_UNSAFE.putIntVolatile(value, ageOffset, 11);
        System.out.println("value:" + value.volatileValue);
    }

    /**
     * 附加了'volatile'加载语义，也就是设置值的时候强制(JMM会保证获得锁到释放锁之间所有对象的状态更新都会在锁被释放之后)更新到主存，
     * 从而保证这些变更对其他线程是可见的。
     */
    @Test
    void demoPutObjectVolatile() {

    }

    /**
     * 设置o对象中offset偏移地址offset对应的Object型field的值为指定值x。这是一个有序或者有延迟的putObjectVolatile方法，
     * 并且不保证值的改变被其他线程立即看到。只有在field被volatile修饰并且期望被修改的时候使用才会生效。
     */
    @Test
    void demoPutOrderedObject() {

    }

}
