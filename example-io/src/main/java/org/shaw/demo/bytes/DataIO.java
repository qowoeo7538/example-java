package org.shaw.demo.bytes;

import java.io.*;

/**
 * Created by joy on 17-2-5.
 */
public class DataIO {

    private final static String FILE_NAME = "/home/joy/桌面/a.properties";

    public static void main(String[] args) throws Exception {
        dataOutSteamTest(FILE_NAME, "31231");

        dataInputSteamTest(FILE_NAME);
    }

    /**
     * FileOutputStream演示
     *
     * @param fileName
     * @param content
     * @param <T>
     * @throws IOException
     */
    public static <T> void dataOutSteamTest(String fileName, T content) throws IOException {
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

    public static void dataInputSteamTest(String fileName) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             InputStream dataInputStream = new DataInputStream(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream))) {
            System.out.println(bufferedReader.readLine());
        }
    }
}

