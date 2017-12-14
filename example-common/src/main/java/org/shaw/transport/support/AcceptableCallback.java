package org.shaw.transport.support;

/**
 * @create: 2017-12-13
 * @description:
 */
@FunctionalInterface
public interface AcceptableCallback<T> {

    /**
     * 接收就绪
     */
    T onAcceptable();
}
