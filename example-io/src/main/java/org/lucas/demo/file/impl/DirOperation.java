package org.lucas.demo.file.impl;

import java.io.File;

public class DirOperation {

    /**
     * 遍历目录
     *
     * @param directory 目录
     */
    public static void listDirectory(final File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException("当前目录不存在");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory + "不是目录");
        }

        // String[] files = directoryTest.list(); 返回当前目录文件名字
        // 返回当前目录文件对象
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listDirectory(file);
                }
                System.out.println(file);
            }
        }
    }

}
