package org.shaw.nio.util.file;

import org.shaw.nio.util.file.impl.CopyFile;
import org.shaw.util.exception.Exceptions;
import org.shaw.util.file.impl.CopyFile;

/**
 * @create: 2017-11-07
 * @description: 文件处理工具类
 */
public class FileUtils {

    public static void copyFile(String src, String copySrc) {
        CopyFile copyFile = new CopyFile(src, copySrc, 10);
        try {
            copyFile.copyFile();
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

}
