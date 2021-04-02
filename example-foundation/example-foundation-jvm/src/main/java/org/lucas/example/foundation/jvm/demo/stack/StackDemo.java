package org.lucas.example.foundation.jvm.demo.stack;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.jvm.demo.stack.support.AStackContent;
import org.lucas.example.foundation.jvm.demo.stack.support.BStackContent;

/**
 * 运行Stack信息
 */
public class StackDemo {

    @Test
    public void stackInfo() {
        AStackContent stackContent = new AStackContent();
        stackContent.AATest();
    }

    @Test
    public void stackInfo1() {
        BStackContent stackContent1 = new BStackContent();
        stackContent1.BATest();
    }
}
