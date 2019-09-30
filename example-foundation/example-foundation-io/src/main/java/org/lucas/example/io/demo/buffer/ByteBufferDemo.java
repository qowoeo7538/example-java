package org.lucas.example.io.demo.buffer;

import java.nio.ByteBuffer;

/**
 * @create: 2017-12-08
 * @description:
 */
public class ByteBufferDemo {
    public static void main(String[] args) {
        ByteBuffer buff = ByteBuffer.allocate(48);
        buff.put((byte) 1);
    }
}
