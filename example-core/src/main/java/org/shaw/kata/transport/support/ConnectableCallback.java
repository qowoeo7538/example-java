package org.shaw.kata.transport.support;

/**
 * @create: 2017-12-13
 * @description:
 */
@FunctionalInterface
public interface ConnectableCallback<T> {
    /**
     * 连接就绪
     */
    T onConnect();
}
