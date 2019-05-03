package org.lucas.kata.enumerate;

/**
 * map形式的枚举
 */
public enum MapKata {
    GET("GET", "GET请求"),
    HEAD("HEAD", "HEAD请求"),
    POST("POST", "POST请求"),
    PUT("PUT", "PUT请求"),
    PATCH("PATCH", "PATCH请求"),
    DELETE("DELETE", "DELETE请求"),
    OPTIONS("OPTIONS", "OPTIONS请求"),
    TRACE("TRACE", "TRACE请求");

    private final String key;
    private final String value;

    MapKata(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
