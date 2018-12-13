package org.shaw.api.String;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 正则匹配
 */
public class PatternDemo {

    private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([$_a-zA-Z][$_a-zA-Z0-9\\.]*);");

    private static final String STR = "package org.shaw.lang.string; class java.util.regex.Matcher";

    public static void main(String[] args) {
        regularPattern(PACKAGE_PATTERN, STR);
    }

    /**
     * 按照正则匹配 String
     * 1)空格会自动做一次分割
     *
     * @param pattern 正则
     * @param str     匹配的 String
     */
    private static void regularPattern(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
