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
        split(DEMO_STR, DELIM, false);
    }

    /**
     * 将字符串进行分隔
     *
     * @param str          字符串
     * @param delim        分隔符 (默认空格、制表符('\t')、换行符('\n')、回车符('\r'))
     * @param returnDelims 是否返回分隔符 默认 {@code false}
     */
    private static void split(String str, String delim, boolean returnDelims) {
        StringTokenizer st = new StringTokenizer(str, delim, returnDelims);
        // 判断是否还有分隔符。
        while (st.hasMoreTokens()) {
            // 获取从当前位置到下一个分隔符的字符串。
            System.out.println("Token:" + st.nextToken());
        }
    }
}
