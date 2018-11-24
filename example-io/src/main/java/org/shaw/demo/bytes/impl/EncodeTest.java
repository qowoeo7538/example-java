package org.shaw.demo.bytes.impl;

import java.io.UnsupportedEncodingException;

public class EncodeTest {

    /**
     * 将字节进行编码
     *
     * @param str     字符串
     * @param charset 编码(默认项目编码)
     * @throws Exception
     */
    public static void encode(String str, CharsetName charset) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes();
        if (charset == null) {
            // 使用默认项目编码
            String string = new String(bytes);
            System.out.println(string);
        } else {
            String string1 = new String(bytes, charset.getCode());
            System.out.println(charset.getCode() + ":" + string1);
        }
    }

    /**
     * 16进制编码
     *
     * @throws Exception
     */
    public static void decode(String str, CharsetName charset) throws Exception {
        byte[] bytes;
        if (charset == null) {
            // 默认使用项目编码
            bytes = str.getBytes();

        } else {
            // 指定编码
            bytes = str.getBytes(charset.getCode());
        }
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(Integer.toHexString(bytes[i] & 0xff) + " ");
            if (i == bytes.length - 1) {
                System.out.println();
            }
        }
    }

}
