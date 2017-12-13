package org.shaw.transport.support;

/**
 * @create: 2017-12-13
 * @description:
 */
@FunctionalInterface
public interface WritableCallback {
    /**
     * 写入就绪
     */
    void onWritable();
}
