package org.shaw.demo.buffer.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {

    public static void copyFileByBuffer(String srcFile, String copyFile) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(srcFile));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(copyFile))) {
            int bte;
            while ((bte = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(bte);
                //刷新缓冲区
                bufferedOutputStream.flush();
            }
        }
    }

}
