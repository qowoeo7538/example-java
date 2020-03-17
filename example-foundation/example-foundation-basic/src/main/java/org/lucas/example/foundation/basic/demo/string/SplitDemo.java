package org.lucas.example.foundation.basic.demo.string;

import org.junit.jupiter.api.Test;

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

    /**
     * 将字符产进行分割
     */
    @Test
    public void demoSplit() {
        /**
         * @param str
         * @param delim        分隔符 (默认空格、制表符('\t')、换行符('\n')、回车符('\r'))
         * @param returnDelims 是否返回分隔符 默认 {@code false}
         */
        StringTokenizer st = new StringTokenizer(DEMO_STR, DELIM, false);
        // 判断是否还有分隔符。
        while (st.hasMoreTokens()) {
            // 获取从当前位置到下一个分隔符的字符串。
            System.out.println("Token:" + st.nextToken());
        }
    }

    /**
     * 正则将字符串分割
     */
    @Test
    public void demoPatternSplit(){
        String[] strings = NAME_SEPARATOR.split(DEMO_STR);
        for (int i = 0, length = strings.length; i < length; i++) {
            System.out.println(strings[i]);
        }
    }
}
