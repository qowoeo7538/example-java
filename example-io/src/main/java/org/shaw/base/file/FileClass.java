package org.shaw.base.file;

import java.io.File;

/**
 * Created by joy on 17-2-3.
 */
public class FileClass {
    public static void main(String[] args) throws Exception {
        //系统分隔符或使用
        String separator = File.separator;
        /** 目录 */
        // 分隔符根据系统使用"/"或者"\\"
        File directory = new File("/home/joy/桌面/DirectoryTest");
        directoryTest(directory);

        /** 文件 */
        File file = new File("/home/joy/桌面/FileTest");
        fileTest(file);

        System.out.println(file);
        System.out.println(file.getName());
        System.out.println(file.getParent());
        System.out.println(file.getParentFile());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getAbsoluteFile());

    }

    /**
     * 文件操作
     */
    public static void fileTest(File file) throws Exception {
        // 是否是文件
        if (file.exists() && file.isFile()) {
            // 删除文件
            file.delete();
        } else {
            //创建文件
            file.createNewFile();
        }
    }

    /**
     * 目录操作
     *
     * @param directory
     */
    public static void directoryTest(File directory) {
        //判断目录是否存在
        if (directory.exists() && directory.isDirectory()) {
            //删除目录
            directory.delete();
        } else {
            //创建目录
            directory.mkdir();
        }
        // file.mkdirs() 多级目录创建;
    }

    /**
     * 列出当前目录文件及子目录文件
     *
     * @param directory
     */
    public static void listDirectory(File directory) throws Exception {
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
                } else {
                    System.out.println(file);
                }
            }
        }
    }
}
