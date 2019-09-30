package org.lucas.example.io.demo.character;

import java.io.*;

/**
 * Created by joy on 17-2-8.
 */
public class IsrAndOswDemo {
    public static void main(String[] args) throws Exception {
        inputStreamReaderDemo("/home/joy/桌面/a.properties", "/home/joy/桌面/aaaaaaaaaaaaa.properties");
    }

    /**
     * 文本文件拷贝
     *
     * @param copyData
     * @param srcData
     * @throws Exception
     */
    public static void inputStreamReaderDemo(String srcData, String copyData) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(new File(srcData));
             FileOutputStream fileOutputStream = new FileOutputStream(new File(copyData));
             // 默认项目的编码,操作的时候，要写文件本身的编码格式
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream)) {

            char[] chars = new char[8 * 1024];

            int i;
            while ((i = inputStreamReader.read(chars, 0, chars.length)) != -1) {
                String str = new String(chars, 0, i);
                System.out.println(str);

                outputStreamWriter.write(chars, 0, i);
                outputStreamWriter.flush();
            }
        }
    }
}
