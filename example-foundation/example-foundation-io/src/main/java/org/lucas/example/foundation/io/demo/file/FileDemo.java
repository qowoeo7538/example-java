package org.lucas.example.foundation.io.demo.file;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.io.demo.file.support.DirOperation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by joy on 17-2-3.
 */
public class FileDemo {

    /**
     * 系统分隔符或者根据系统使用"/"或者"\\"
     */
    private static final String separator = File.separator;

    /**
     * 目录
     */
    private static final File DIRECTORY = new File("/home/shaw/桌面/DirectoryTest");

    /**
     * 文件
     */
    private static final File FILE = new File("/home/shaw/桌面/FileTest");

    /**
     * 拷贝源文件
     */
    private static final String SOURCE = "/home/shaw/桌面/FileTest";

    /**
     * 拷贝目标文件
     */
    private static final String TARGET = "/home/shaw/桌面/FileTest";

    /**
     * 获取文件信息
     */
    @Test
    public void getInfo() {
        System.out.println(FILE);
        System.out.println(FILE.getName());
        System.out.println(FILE.getParent());
        System.out.println(FILE.getParentFile());
        System.out.println(FILE.getAbsolutePath());
        System.out.println(FILE.getAbsoluteFile());
    }

    /**
     * 文件操作
     */
    @Test
    public void fileTest() throws Exception {
        // 是否是文件
        if (FILE.exists() && FILE.isFile()) {
            // 删除文件
            FILE.delete();
        } else {
            // 创建文件
            FILE.createNewFile();
        }
    }

    /**
     * 目录操作
     */
    @Test
    public void directoryTest() {
        //判断目录是否存在
        if (DIRECTORY.exists() && DIRECTORY.isDirectory()) {
            //删除目录
            DIRECTORY.delete();
        } else {
            //创建目录
            DIRECTORY.mkdir();
        }
        // file.mkdirs() 多级目录创建;
    }

    /**
     * 读取文件所有的字节
     */
    @Test
    public void classLoaderFile() throws IOException {
        // 读取当前执行文件目录下的文件
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("logging.properties")) {
            // 获取输入流的所有字节
            byte[] bytes = in.readAllBytes();
            String str = new String(bytes, "utf-8");
            System.out.println(str);
        }
    }

    /**
     * 列出当前目录文件及子目录文件
     */
    @Test
    public void iteratorDirectory() {
        DirOperation.listDirectory(DIRECTORY);
    }

    /**
     * 文件拷贝(API方式)
     *
     * @throws IOException
     */
    @Test
    public void systemCopyFile() {
        try {
            Path sourcePath = Paths.get(SOURCE);
            Path targetPath = Paths.get(TARGET);
            /**
             * @param target 目标文件
             * @param source 源文件
             */
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
