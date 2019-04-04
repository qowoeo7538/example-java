package org.shaw.stack;

import org.junit.Test;
import org.shaw.stack.impl.AStackContent;
import org.shaw.stack.impl.BStackContent;

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
