package org.lucas.example.core.transport.support;

import java.nio.channels.ScatteringByteChannel;

/**
 * @create: 2017-12-13
 * @description:
 */
@FunctionalInterface
public interface ReadableCallback<T extends ScatteringByteChannel> {
    /**
     * 读取就绪
     */
    void onReadable(T t);
}
