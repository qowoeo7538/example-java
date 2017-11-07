package org.shaw.io.bytes;

import java.io.*;

/**
 * Created by joy on 17-2-5.
 */
public class DataIO {
    public static void main(String[] args) throws Exception {
        dataOutSteamTest("/home/joy/桌面/a.properties","31231");

        dataInputSteamTest("/home/joy/桌面/a.properties");
    }

    /**
     * FileOutputStream演示
     *
     * @param fileName
     * @param content
     * @param <T>
     * @throws IOException
     */
    public static <T> void dataOutSteamTest(String fileName,T content)throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        if(content instanceof Integer){
            dataOutputStream.writeInt((Integer) content);
        }
        if (content instanceof Long) dataOutputStream.writeLong((Long) content);
        if (content instanceof Double) dataOutputStream.writeDouble((Double) content);
        if(content instanceof Character || content instanceof String){
            dataOutputStream.writeUTF((String) content);
        }
        dataOutputStream.close();
        fileOutputStream.close();
    }

    public static void dataInputSteamTest(String fileName)throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        InputStream dataInputStream = new DataInputStream(fileInputStream);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
        System.out.println(bufferedReader.readLine());

        bufferedReader.close();
        dataInputStream.close();
        fileInputStream.close();
    }
}

