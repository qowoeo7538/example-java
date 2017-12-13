package org.shaw.transport.support;

/**
 * @create: 2017-12-13
 * @description:
 */
@FunctionalInterface
public interface ConnectableCallback {
    /**
     * 连接就绪
     */
    void onConnect();
}
