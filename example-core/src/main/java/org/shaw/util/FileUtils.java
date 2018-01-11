package org.shaw.util;

import org.shaw.util.support.CopyFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @create: 2017-11-07
 * @description: 文件处理工具类
 */
public abstract class FileUtils {

    public static void copyFile(String src, String copySrc) {
        CopyFile copyFile = new CopyFile(src, copySrc, 10);
        try {
            copyFile.copyFile();
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 保存临时文件
     *
     * @param sourceFile
     * @param tempFile
     * @throws Exception
     */
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

}
