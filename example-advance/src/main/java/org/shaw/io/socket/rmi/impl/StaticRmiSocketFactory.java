package org.shaw.io.socket.rmi.impl;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;

/**
 * @create: 2017-11-28
 * @description:
 */
public class StaticRmiSocketFactory implements RMIClientSocketFactory, RMIServerSocketFactory, Serializable {

    private String host;

    private int port;

    public StaticRmiSocketFactory(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        System.out.println("create client socket " + this.host + ":" + this.port);
        return new Socket(this.host, this.port);
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        System.out.println("create server socket " + this.host + ":" + this.port);
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(this.port));
        return serverSocket;
    }
}
