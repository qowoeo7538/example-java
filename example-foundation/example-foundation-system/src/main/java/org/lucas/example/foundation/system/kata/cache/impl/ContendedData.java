package org.lucas.example.foundation.system.kata.cache.impl;

import jdk.internal.vm.annotation.Contended;

/**
 * 类前加上代表整个类的每个变量都会在单独的cache line中
 */
@Contended
public class ContendedData {
    int value;
    long modifyTime;
    boolean flag;
    long createTime;
    char key;
}

class ContendedData1 {
    @Contended("group1")
    int value;
    @Contended("group1")
    long modifyTime;
    @Contended("group2")
    boolean flag;
    @Contended("group3")
    long createTime;
    @Contended("group3")
    char key;
}
