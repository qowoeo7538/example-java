package org.lucas.example.foundation.jvm.demo.reference;

import org.junit.jupiter.api.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 幻象引用（通过 -XX:+PrintGCDetails 显示GC相关日志）
 */
public class PhantomReferenceDemo {

    /**
     *
     */
    @Test
    public void phantomReferenceTest() {
        Object obj = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> phantom = new PhantomReference<>(obj, queue);
        System.gc();
        System.out.println(phantom.get());
    }
}
