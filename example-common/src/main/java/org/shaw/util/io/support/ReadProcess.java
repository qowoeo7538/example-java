package org.shaw.util.io.support;

import java.nio.ByteBuffer;

/**
 * @create: 2017-12-19
 * @description: 缓存读取处理
 */
@FunctionalInterface
public interface ReadProcess<T> {

    /**
     * 缓存读取处理
     *
     * @param buffer 处理缓存
     * @return
     */
    T onProcess(ByteBuffer buffer);
}
