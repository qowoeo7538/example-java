package org.lucas.demo.unsafe.impl;

/**
 * @Author: shaw
 * @Date: 2019/5/17 15:13
 */
public class Value {

    /**
     * 保证该值在每次使用的时候都进行检查.
     */
    public volatile long value;

}
