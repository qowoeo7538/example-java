package org.lucas.spi.impl;

public class ServerB implements IServer {

    private final static String SERVER_NAME = "B";

    @Override
    public boolean isSupported(String serverName) {
        if (SERVER_NAME.equalsIgnoreCase(serverName)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void doSomething() {
        System.out.println("it is B server!");
    }
}
