package org.lucas.example.jvm.gc;

import org.junit.jupiter.api.Test;
import org.lucas.example.jvm.gc.impl.*;

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

    /**
     * 通过 finalize 重新关联引用，逃脱GC回收。
     *
     * @since 9
     */
    @Test
    public void escapeGC() throws Throwable {
        FinalizeEscapeGC.SAVE_HOOK = new FinalizeEscapeGC();
        // 对象第一次成功拯救自己
        FinalizeEscapeGC.SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级很低，所以暂停等待它
        Thread.sleep(500);
        if (FinalizeEscapeGC.SAVE_HOOK != null) {
            FinalizeEscapeGC.SAVE_HOOK.isAlive();
        } else {
            System.out.println("no,i am dead :(");
        }
        // =====================================
        // 这次逃脱失败了，因为finalize方法只会被系统自动调用一次。
        FinalizeEscapeGC.SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级很低，所以暂停等待它
        Thread.sleep(500);
        if (FinalizeEscapeGC.SAVE_HOOK != null) {
            FinalizeEscapeGC.SAVE_HOOK.isAlive();
        } else {
            System.out.println("no,i am dead :(");
        }
    }

}