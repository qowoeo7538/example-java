package org.shaw.advance.io;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by joy on 17-4-3.
 */
public class StringToByte {
    public static void main(String[] args) throws Exception {
        File sourceFile = new File("/home/joy/图片/img1-lg.jpg");
        File tempFile = new File("/home/joy/桌面/TempFile");
        saveTempFile(sourceFile, tempFile);
        String str = byteToString(tempFile);
        File saveFile = new File("/home/joy/桌面/saveFile.jpg");
        StringTobyte(str, saveFile);
    }

    // 保存临时文件
    public static void saveTempFile(File sourceFile, File tempFile) throws Exception {
        FileInputStream inputStream = new FileInputStream(sourceFile);
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        byte[] buf = new byte[10 * 1024];
        int readLeng = 0;
        while ((readLeng = inputStream.read(buf)) != -1) {
            fileOutputStream.write(buf, 0, readLeng);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
        inputStream.close();
    }

    // 文件字节转字符串
    public static String byteToString(File file) throws Exception {
        StringBuilder returnDatas = new StringBuilder();
        FileInputStream fileInputStream = new FileInputStream(file);
        BASE64Encoder base64 = new BASE64Encoder();
        byte[] buf = new byte[10 * 1024];
        int readLenth = 0;
        while ((readLenth = fileInputStream.read(buf)) != -1) {
            byte[] copyByte = new byte[readLenth - 1];
            for (int i = 0; i < copyByte.length; i++) {
                copyByte[i] = buf[i];
            }
            returnDatas.append(base64.encode(copyByte));
        }
        fileInputStream.close();
        return returnDatas.toString();
    }

    // 字符串转文件
    public static void StringTobyte(String str, File file) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BASE64Decoder base64 = new BASE64Decoder();
        fileOutputStream.write(base64.decodeBuffer(str));
        fileOutputStream.close();
    }
}
