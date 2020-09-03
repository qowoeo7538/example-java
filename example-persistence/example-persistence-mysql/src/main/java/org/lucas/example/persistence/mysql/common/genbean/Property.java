package org.lucas.example.persistence.mysql.common.genbean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Property {

    private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

    private String name;

    private String type;

    private String comment;

    public String getName() {
        return lineToHump(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
