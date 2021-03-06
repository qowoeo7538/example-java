package org.lucas.example.foundation.core.util;

import org.lucas.component.common.io.support.SegmentCopyFile;
import org.lucas.component.common.util.ExceptionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 文件处理工具类
 */
public abstract class FileUtils {

    public static void copyFile(String src, String copySrc) {
        SegmentCopyFile copyFile = new SegmentCopyFile(src, copySrc, 10);
        try {
            copyFile.copyFile();
        } catch (Exception e) {
            throw ExceptionUtils.unchecked(e);
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
        try (FileInputStream inputStream = new FileInputStream(sourceFile);
             FileOutputStream fileOutputStream = new FileOutputStream(tempFile)
        ) {
            byte[] buf = new byte[10 * 1024];
            int readLength;
            while ((readLength = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, readLength);
                fileOutputStream.flush();
            }
        }
    }

}
