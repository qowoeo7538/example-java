package org.lucas.example.design.pattern.spi;

import org.lucas.example.design.pattern.spi.impl.ExtensionFactory;
import org.lucas.example.design.pattern.spi.impl.IServer;

/**
 *
 */
public class SpiDemo {

    private final static String ENCODING_NAME = ServerType.B_SERVER.name;

    public static void main(String[] args) {
        IServer server = ExtensionFactory.getInstance(ENCODING_NAME);
        server.doSomething();
    }

    private enum ServerType {

        A_SERVER("A"),
        B_SERVER("B");

        private final String name;

        ServerType(String name) {
            this.name = name;
        }
    }
}
