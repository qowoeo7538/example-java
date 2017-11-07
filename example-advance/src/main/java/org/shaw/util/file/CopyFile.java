package org.shaw.util.file;

import org.shaw.io.IOUtil;

import java.io.RandomAccessFile;

/**
 * @create: 2017-11-07
 * @description: 分段拷贝文件
 */
public class CopyFile extends Thread {
    private String srcName;
    private String copyName;
    private long fileSize;
    private int writeSize;
    private int block;

    public CopyFile(String srcName, String copyName, long fileSize, int block) {
        this.fileSize = fileSize;
        this.writeSize = (int) fileSize / 5;
        this.block = block;
        this.srcName = srcName;
        this.copyName = copyName;
    }

    @Override
    public void run() {
        try {
            RandomAccessFile randomAccessFileRead = new RandomAccessFile(srcName, "r");
            byte[] bytes = null;
            randomAccessFileRead.seek((block - 1) * writeSize);
            if (block <= 5) {
                bytes = new byte[writeSize];
            } else {
                bytes = new byte[(int) fileSize % 5];
            }
            randomAccessFileRead.read(bytes, 0, writeSize);
            randomAccessFileRead.close();

            RandomAccessFile randomAccessFileWrite = new RandomAccessFile(copyName, "rw");
            randomAccessFileWrite.seek((block - 1) * writeSize);
            randomAccessFileWrite.write(bytes);
            randomAccessFileWrite.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 分段拷贝文件
     */
    public static void copyFile(String srcFile, String copyFile) throws Exception {
        long fileSize = IOUtil.getFileSize(srcFile);
        new CopyFile(srcFile, copyFile, fileSize, 1).start();
        new CopyFile(srcFile, copyFile, fileSize, 2).start();
        new CopyFile(srcFile, copyFile, fileSize, 3).start();
        new CopyFile(srcFile, copyFile, fileSize, 4).start();
        new CopyFile(srcFile, copyFile, fileSize, 5).start();
        if (fileSize % 5 != 0) {
            new CopyFile(srcFile, copyFile, fileSize, 5).start();
        }
    }

    /**
     * 发生异常将会进行回调，线程池并不会立即生效
     *
     * @param eh
     */
    @Override
    public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh) {
        super.setUncaughtExceptionHandler(eh);
    }
}
