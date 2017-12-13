package org.shaw.transport.support;

/**
 * @create: 2017-12-13
 * @description:
 */
@FunctionalInterface
public interface ReadableCallback {
    /**
     * 读取就绪
     */
    void onReadable();
}
