package org.shaw.transport.support;

/**
 * @create: 2017-12-13
 * @description:
 */
@FunctionalInterface
public interface AcceptableCallback {

    /**
     * 接收就绪
     */
    void onAcceptable();
}
