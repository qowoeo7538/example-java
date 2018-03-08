package org.shaw.lang.string;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * @create: 2018-03-02
 * @description:
 */
public class SplitDemo {

    private final static String DEMO_STR = "www.ooobj,sdfs,com";

    private final static String DELIM = ".b";

    private final static Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

    public static void main(String[] args) {
        System.out.println("=============== Tokenizer将字符串进行分隔 ===============");
        stringTokenizerSplit(DEMO_STR, DELIM, false);

        System.out.println("=============== 正则将字符串进行分隔 ===============");
        patternSplit(DEMO_STR, NAME_SEPARATOR);
    }

    /**
     * 将字符串进行分隔
     *
     * @param str          字符串
     * @param delim        分隔符 (默认空格、制表符('\t')、换行符('\n')、回车符('\r'))
     * @param returnDelims 是否返回分隔符 默认 {@code false}
     */
    private static void stringTokenizerSplit(String str, String delim, boolean returnDelims) {
        StringTokenizer st = new StringTokenizer(str, delim, returnDelims);
        // 判断是否还有分隔符。
        while (st.hasMoreTokens()) {
            // 获取从当前位置到下一个分隔符的字符串。
            System.out.println("Token:" + st.nextToken());
        }
    }

    private static void patternSplit(String str, Pattern pattern) {
        String[] strings = pattern.split(str);
        for (int i = 0, length = strings.length; i < length; i++) {
            System.out.println(strings[i]);
        }
    }
}
