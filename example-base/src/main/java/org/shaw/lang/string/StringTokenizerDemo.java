package org.shaw.lang.string;

import java.util.StringTokenizer;

/**
 * @create: 2018-03-02
 * @description:
 */
public class StringTokenizerDemo {

    private final static String DEMO_STR = "www.ooobj.com";

    private final static String DELIM = ".b";

    public static void main(String[] args) {
        // 将字符串进行分隔
        System.out.println("=============== 将字符串进行分隔 ===============");
        split(DEMO_STR, DELIM);
    }

    /**
     * 将字符串进行分隔
     *
     * @param str   字符串
     * @param delim 分隔符
     */
    private static void split(String str, String delim, boolean returnDelims) {
        StringTokenizer st = new StringTokenizer(str, delim);
        // 判断是否有下一个元素
        while (st.hasMoreTokens()) {
            System.out.println("Token:" + st.nextToken());
        }
    }
}
