package org.shaw.io.bytes;

import org.shaw.io.IOUtil;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by joy on 17-2-3.
 */
public class RandomAccessFileClass {
    public static void main(String[] args) throws Exception{
        copyFile("/home/joy/桌面/15-反向代理实现nginx+apache动静分离.wmv","/home/joy/桌面/dddddddd.wmv");
    }

    /**
     * int类型写入
     * @param file
     * @param i
     * @throws Exception
     */
    public static long intWrite(File file,int i) throws Exception {
        if (!file.exists()) {
            throw new IllegalArgumentException("当前文件不存在！");
        }
        if (!file.isFile()) throw new IllegalArgumentException(file + "不是文件！");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
        // 序列化
        randomAccessFile.write((i >>> 24) & 0xFF); // 右移24位，&0xff 屏蔽高24位;
        randomAccessFile.write((i >>> 16) & 0xFF); // 右移16位，&0xff 屏蔽高24位;
        randomAccessFile.write((i >>> 8) & 0xFF); // 右移8位，&0xff 屏蔽高24位;
        randomAccessFile.write((i >>> 0) & 0xFF); // 右移0位，&0xff 屏蔽高24位;
        // randomAccessFile.writeInt(i); 直接写入
        long pointer = randomAccessFile.getFilePointer();
        randomAccessFile.close();
        return pointer;
    }

    /**
     * 写入一个字符
     * @param file
     * @param c
     * @param encode
     * @return
     * @throws Exception
     */
    public static long charWrite(File file,Character c,String encode) throws Exception {
        if (!file.exists()) {
            throw new IllegalArgumentException("当前文件不存在！");
        }
        if (!file.isFile()) throw new IllegalArgumentException(file + "不是文件！");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
        byte[] bytes = IOUtil.getChartoBytes(c,encode);
        randomAccessFile.write(bytes);
        long pointer = randomAccessFile.getFilePointer();
        randomAccessFile.close();
        return pointer;
    }

    /**
     * 读取文件
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] readFile(File file) throws Exception {
        if (!file.exists()) {
            throw new IllegalArgumentException("当前文件不存在！");
        }
        if (!file.isFile()) throw new IllegalArgumentException(file + "不是文件！");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
        randomAccessFile.seek(0); //读文件，必须把指针移到头部
        byte[] bytes = new byte[(int) (randomAccessFile.length())];
        randomAccessFile.read(bytes);
        String str = new String(bytes,"utf-8");
        System.out.println(str);
        randomAccessFile.close();
        return bytes;
    }

    /**
     * 分段拷贝文件
     */
    public static void copyFile(String srcFile,String copyFile) throws Exception {
        long fileSize = IOUtil.getFileSize(srcFile);
        new CopyFile(srcFile,copyFile,fileSize,1).start();
        new CopyFile(srcFile,copyFile,fileSize,2).start();
        new CopyFile(srcFile,copyFile,fileSize,3).start();
        new CopyFile(srcFile,copyFile,fileSize,4).start();
        new CopyFile(srcFile,copyFile,fileSize,5).start();
        if (fileSize%5!=0){
            new CopyFile(srcFile,copyFile,fileSize,5).start();
        }
    }
}

/**
 * 分段拷贝文件
 */
class CopyFile extends Thread{
    String srcName;
    String copyName;
    long fileSize;
    int writeSize;
    int block;

    public CopyFile(String srcName,String copyName,long fileSize,int block){
        this.fileSize = fileSize;
        this.writeSize = (int) fileSize/5;
        this.block = block;
        this.srcName = srcName;
        this.copyName = copyName;
    }

    @Override
    public void run() {
        try {
            RandomAccessFile randomAccessFileRead = new RandomAccessFile(srcName,"r");
            byte[] bytes = null;
            randomAccessFileRead.seek((block-1)*writeSize);
            if(block<=5){
                bytes = new byte[writeSize];
            }else {
                bytes = new byte[(int) fileSize%5];
            }
            randomAccessFileRead.read(bytes,0,writeSize);
            randomAccessFileRead.close();

            RandomAccessFile randomAccessFileWrite = new RandomAccessFile(copyName,"rw");
            randomAccessFileWrite.seek((block-1)*writeSize);
            randomAccessFileWrite.write(bytes);
            randomAccessFileWrite.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * 发生异常将会进行回调，线程池并不会立即生效
     * @param eh
     */
    @Override
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
        super.setUncaughtExceptionHandler(eh);
    }
}
