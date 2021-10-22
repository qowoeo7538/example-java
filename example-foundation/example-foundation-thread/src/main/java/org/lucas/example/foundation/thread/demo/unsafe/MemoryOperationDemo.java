package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

/**
 * 堆外内存操作
 * <p>
 * 1. 对垃圾回收停顿的改善。由于堆外内存是直接受操作系统管理而不是JVM，所以当我们使用堆外内存时，即可保持较小的堆内内存规模。从而在GC时减少回收停顿对于应用的影响。
 * 2. 提升程序I/O操作的性能。通常在I/O通信过程中，会存在堆内内存到堆外内存的数据拷贝操作，对于需要频繁进行内存间数据拷贝且生命周期较短的暂存数据，都建议存储到堆外内存。
 */
class MemoryOperationDemo {

    private static final Unsafe THE_UNSAFE = UnsafeUtils.getUnsafe();

    /**
     * 内存属性
     */
    @Test
    void demoSystemProperty() {
        // 返回系统指针的大小。返回值为4（32位系统）或 8（64位系统）。
        int addressSize = THE_UNSAFE.addressSize();
        System.out.println(addressSize);

        // 内存页的大小，此值为2的幂次方。
        int pageSize = THE_UNSAFE.pageSize();
        System.out.println(pageSize);
    }

    /**
     * 内存值设置
     */
    @Test
    void demoMemorySet() {
        long address = 0;
        try {
            // 1. 分配8字节内存，并返回基地址
            address = THE_UNSAFE.allocateMemory(8L);
            System.out.println("首地址:" + address);

            // 2. 初始化内存
            THE_UNSAFE.setMemory(address, 8L, (byte) 0);
            System.out.println("设置内存地址的值:" + THE_UNSAFE.getByte(address));

            // 3. 设置内存地址的值为10
            THE_UNSAFE.putInt(address, 10);
            System.out.println("设置内存地址的值:" + THE_UNSAFE.getInt(address));

            // 4. 设置内存地址 + 4 的值为11
            THE_UNSAFE.putInt(address + 4, 11);
            System.out.println("设置内存地址 + 4 的值:" + THE_UNSAFE.getInt(address + 4));

            // 5. 设置内存地址 + 8 的值为12
            THE_UNSAFE.putInt(address + 8, 12);
            System.out.println("设置内存地址 + 8 的值:" + THE_UNSAFE.getInt(address + 8));

            // 6. 覆盖起始地址值为81
            THE_UNSAFE.putLong(address, 81L);
            System.out.println("覆盖起始地址值:" + THE_UNSAFE.getLong(address));
        } finally {
            if (address != 0) {
                THE_UNSAFE.freeMemory(address);
            }
        }

    }

    @Test
    void demoMemoryCopy() {
        long address = 0;
        long newAddress = 0;
        try {
            // 1. 分配8字节内存，并返回基地址
            address = THE_UNSAFE.allocateMemory(8L);

            // 2. 初始化内存
            THE_UNSAFE.setMemory(address, 8L, (byte) 0);

            // 3. 覆盖起始地址值为81
            THE_UNSAFE.putLong(address, 81L);
            System.out.println("旧地址值:" + THE_UNSAFE.getLong(address));

            // 4. 分配一块新内存
            newAddress = THE_UNSAFE.allocateMemory(8L);

            //copy内存
            THE_UNSAFE.copyMemory(address, newAddress, 8L);
            System.out.println("新地址值:" + THE_UNSAFE.getLong(newAddress));
        } finally {
            THE_UNSAFE.freeMemory(address);
            THE_UNSAFE.freeMemory(newAddress);
        }

    }

}
