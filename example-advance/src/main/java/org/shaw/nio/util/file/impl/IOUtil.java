package org.shaw.nio.util.file.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * Created by joy on 17-2-5.
 */
public class IOUtil {
    /**
     * char转换byte[]
     *
     * @param chars
     * @param encode
     * @return
     */
    public static byte[] getChartoBytes(char chars, String encode) {
        Charset cs = Charset.forName(encode);
        //分配缓冲区
        CharBuffer cb = CharBuffer.allocate(1);
        //放入缓冲区
        cb.put(chars);
        //为一系列通道写入或相对获取 操作做好准备
        cb.flip();
        //将此 charset 中的字符串编码成字节的便捷方法。
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    /**
     * @param fileName
     * @return 以字节为单位返回文件大小;
     */
    public static long getFileSize(String fileName) throws Exception {
        File file = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        // return file.length();
        return fileChannel.size();
    }

    /**
     * 计算文件的哈希值
     *
     * @param file
     * @param hashType "MD5"，"SHA1","SHA-1"，"SHA-256"，"SHA-384"，"SHA-512"
     * @return
     */
    public static String getFileMD5(File file, String hashType) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance(hashType);
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
