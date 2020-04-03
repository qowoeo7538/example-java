package org.lucas.example.foundation.jvm.demo.load.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @create: 2017-11-08
 * @description: 自定义类加载器加载类
 */
public class MyClassLoader extends ClassLoader {
    public String _filePath;

    public String _name;

    public MyClassLoader() {
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    public MyClassLoader(String filePath) {
        this._filePath = filePath;
    }

    @Override
    public synchronized Class<?> loadClass(String name) throws ClassNotFoundException {
        _name = name;
        File file = getClassFile();
        try {
            byte[] bytes = getClassToBytes(file);
            Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
            return c;
        } catch (Exception e) {
            return super.loadClass(name);
        }
    }

    private File getClassFile() {
        String fileName = _filePath + _name.substring(_name.lastIndexOf(".") + 1) + ".class";
        return new File(fileName);
    }

    /**
     * 将Class文件转换成byte数组
     *
     * @param file Class文件
     * @return byte[]
     * @throws IOException
     */
    public byte[] getClassToBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             FileChannel fc = fis.getChannel();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             WritableByteChannel wbc = Channels.newChannel(baos)
        ) {
            ByteBuffer by = ByteBuffer.allocate(1024);
            while (true) {
                int i = fc.read(by);
                if (i == 0 || i == -1) {
                    break;
                }
                by.flip();
                wbc.write(by);
                by.clear();
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    public void set_filePath(String filePath) {
        this._filePath = filePath;
    }
}
