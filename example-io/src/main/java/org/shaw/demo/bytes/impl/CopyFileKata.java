package org.shaw.demo.bytes.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFileKata {

    /**
     * 文件拷贝(最佳拷贝方式)
     *
     * @param source 源文件
     * @param target 目标文件
     * @throws IOException
     */
    public static void copyFile(final String source, final String target) throws IOException {
        // 默认为删除文件创建一个新文件，true为追加内容
        try (FileOutputStream fileOutputStream = new FileOutputStream(target);
             FileInputStream fileInputStream = new FileInputStream(source)) {
            byte[] buf = new byte[10 * 1024];
            int y;
            while ((y = fileInputStream.read(buf, 0, buf.length)) != -1) {
                fileOutputStream.write(buf, 0, y);
                fileOutputStream.flush();
                if (fileInputStream.available() < buf.length && fileInputStream.available() > 0) {
                    buf = new byte[fileInputStream.available()];
                }
            }
        }
    }

}
