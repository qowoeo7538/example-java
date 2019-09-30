package org.lucas.example.foundation.io.demo.bytes.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataStream {

    /**
     * @param fileName 文件全名
     * @param content  内容
     * @param <T>      输出类型
     * @throws IOException
     */
    public static <T> void dataOut(String fileName, T content) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)
        ) {
            if (content instanceof Integer) {
                dataOutputStream.writeInt((Integer) content);
            }
            if (content instanceof Long) {
                dataOutputStream.writeLong((Long) content);
            }
            if (content instanceof Double) {
                dataOutputStream.writeDouble((Double) content);
            }
            if (content instanceof Character || content instanceof String) {
                dataOutputStream.writeUTF((String) content);
            }
        }
    }

    /**
     * @param fileName 文件全名
     * @throws IOException
     */
    public static void dataInputSteam(String fileName) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             InputStream dataInputStream = new DataInputStream(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream))) {
            System.out.println(bufferedReader.readLine());
        }
    }
}
