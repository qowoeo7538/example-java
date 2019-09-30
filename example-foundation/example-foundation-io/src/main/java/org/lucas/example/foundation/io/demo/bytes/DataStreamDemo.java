package org.lucas.example.foundation.io.demo.bytes;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.io.demo.bytes.impl.DataStream;

import java.io.IOException;

/**
 * Created by joy on 17-2-5.
 */
public class DataStreamDemo {

    private static final String FILE = "/home/joy/桌面/a.properties";

    /**
     * DataOutputStream
     *
     * @throws IOException
     */
    @Test
    public void dataOutStreamTest() throws IOException {
        DataStream.dataOut(FILE, "213123");
    }

    /**
     * DataInputStream
     *
     * @throws IOException
     */
    @Test
    public void dataInputStreamTest() throws IOException {
        DataStream.dataInputSteam(FILE);
    }
}

