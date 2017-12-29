package org.shaw.base;

/**
 * @create: 2017-12-29
 * @description:
 */
public class JniDemo {
    static {
        System.loadLibrary("Hello");
    }

    public native void DisplayHello();

    /**
     * @param args
     */
    public static void main(String[] args) {
        new JniDemo().DisplayHello();
    }
}
