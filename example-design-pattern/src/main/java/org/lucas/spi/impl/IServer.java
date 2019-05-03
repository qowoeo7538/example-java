package org.lucas.spi.impl;

/**
 * 定义服务接口，其它类只要实现此接口，就会被装载到Reader的运行时。
 */
public interface IServer {

    /**
     * 调用具体实现类
     *
     * @param serverName 服务名
     * @return
     */
    boolean isSupported(String serverName);

    /**
     * 实际实现方法
     */
    void doSomething();
}
