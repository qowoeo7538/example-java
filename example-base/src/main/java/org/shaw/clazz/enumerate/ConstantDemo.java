package org.shaw.clazz.enumerate;

/**
 * 常量的定义
 */
public enum ConstantDemo {
    SUCCESS("Y"),
    FAIL("N"),
    AUDIT_SUCCESS("1"),
    AUDIT_FAIL("0");

    private final String state;

    ConstantDemo(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
