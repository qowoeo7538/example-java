package org.shaw.demo.buffer.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {

    public static void copyFileByBuffer(final String source, final String target) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(source));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(target))) {
            int bte;
            while ((bte = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(bte);
                //刷新缓冲区
                bufferedOutputStream.flush();
            }
        }
    }

}
