package org.lucas.example.design.pattern.spi.impl;

import java.util.ServiceLoader;

/**
 * 利用 java.util.ServiceLoader 来装载服务接口的实现，但在不提供任何实现类。
 */
public abstract class ExtensionFactory {
    /**
     * 获取具体实现类
     *
     * @param serverName 服务名
     * @return
     * @throws Exception
     */
    public static IServer getInstance(String serverName) {
        ServiceLoader<IServer> sl = ServiceLoader.load(IServer.class);
        for (IServer server : sl) {
            if (server.isSupported(serverName)) {
                return server;
            }
        }
        throw new IllegalArgumentException("Not supported server: " + serverName);
    }
}
