package org.lucas.example.design.pattern.spi.impl;

public class ServerA implements IServer {

    private final static String SERVER_NAME = "A";

    /**
     * 判断是否调用该实现类
     *
     * @param serverName 服务名称
     * @return
     */
    @Override
    public boolean isSupported(String serverName) {
        return SERVER_NAME.equalsIgnoreCase(serverName);
    }

    @Override
    public void doSomething() {
        System.out.println("it is A server");
    }
}
