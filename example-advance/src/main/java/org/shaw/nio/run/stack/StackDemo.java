package org.shaw.nio.run.stack;

import org.shaw.nio.run.stack.impl.AStackContent;
import org.shaw.run.stack.impl.AStackContent;
import org.shaw.run.stack.impl.BStackContent;

/**
 * 运行Stack信息
 */
public class StackDemo {
    public static void main(String[] args) {
        AStackContent stackContent = new AStackContent();
        stackContent.AATest();

        BStackContent stackContent1 = new BStackContent();
        stackContent1.BATest();
    }
}
