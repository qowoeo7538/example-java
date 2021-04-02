package org.lucas.example.foundation.system.kata.cache.support;

/**
 * 1. 当value变量改变时，modifyTime会改变
 * 2. createTime变量和key变量在创建后，就不会再变化。
 * 3. flag也经常会变化，不过与modifyTime和value变量毫无关联。
 */
public class DataPadding {
    /**
     * 防止与前一个对象产生伪共享.
     * 按照一行缓存64字节计算，前后填充56字节（7个long），
     * 中间大于等于8字节的内容都能独占一行Cache line
     */
    long a1, a2, a3, a4, a5, a6, a7, a8;
    int value;
    long modifyTime;
    /**
     * 防止不相关变量伪共享;
     */
    long a9, a10, a11, a12, a13, a14, a15, a16;
    boolean flag;
    long b1, b2, b3, b4, b5, b6, b7, b8;
    long createTime;
    char key;
    /**
     * 防止与下一个对象产生伪共享
     */
    long b9, b10, b11, b12, b13, b14, b15, b16;
}
