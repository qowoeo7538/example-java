package org.shaw.base;

/**
 * @create: 2017-12-29
 * @description:
 */
public class JniDemo {

    /**
     * 本地方法调用
     *
     * @param who
     * @param times
     */
    public native void sayHi(String who, int times);

    static {
        // 加载动态链接库到内存
        System.loadLibrary("HelloC");
    }

    public static void main(String[] args) {
        JniDemo hello = new JniDemo();
        System.out.println("Say hello to " + args[0] + " " + args[1] + " times.");
        hello.sayHi(args[0], Integer.parseInt(args[1]));
    }
}
