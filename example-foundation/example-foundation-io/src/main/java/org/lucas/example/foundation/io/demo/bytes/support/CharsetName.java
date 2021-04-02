package org.lucas.example.foundation.io.demo.bytes.support;

public enum CharsetName {

    UTF_16BE("UTF_16BE", "utf-16be"),
    UTF_8("UTF_8", "utf-8");

    private final String name;

    private final String code;

    CharsetName(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
