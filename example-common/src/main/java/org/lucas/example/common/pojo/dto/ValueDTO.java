package org.lucas.example.common.pojo.dto;

/**
 * @Author: shaw
 * @Date: 2019/5/17 15:13
 */
public class ValueDTO {

    public long value;

    /**
     * 保证该值在每次使用的时候都进行检查.
     */
    public volatile long volatileValue;

    public static long staticValue;

    /**
     * 保证该值在每次使用的时候都进行检查.
     */
    public volatile String name = "dog1";

}
