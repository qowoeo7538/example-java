package org.shaw.gc;

import org.junit.Test;
import org.shaw.gc.impl.Book;

/**
 * finalize机制可能会导致性能问题，死锁和线程挂起。
 * finalize中的错误可能导致内存泄漏；
 * 如果不在需要时，也没有办法取消垃圾回收；
 * 并且没有指定不同执行finalize对象的执行顺序。
 * 此外，没有办法保证finlize的执行时间。
 * 遇到这些情况，对象调用finalize方法只有被无限期延后。
 * <p>
 * finalize() 方法在 JDK9 被弃用
 */
public class FinalizeDemo {

    /**
     * 使用 finalize() 检查对象。
     */
    @Test
    public void checkObject() {
        Book novel = new Book(true);
        // Proper cleanup:
        novel.checkIn();
        // Drop the reference, forget to clean up:
        new Book(true);
        // Force garbage collection & finalization:
        System.gc();
    }

}
