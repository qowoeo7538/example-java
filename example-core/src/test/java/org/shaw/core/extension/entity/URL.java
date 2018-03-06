package org.shaw.core.extension.entity;

import org.shaw.common.Constants;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @create: 2018-03-01
 * @description:
 */
public final class URL implements Serializable {

    private final Map<String, String> parameters;

    public URL() {
        this(null);
    }

    public URL(Map<String, String> parameters) {
        if (parameters == null) {
            parameters = new HashMap<>();
        } else {
            parameters = new HashMap<>(parameters);
        }
        // 获取一个不可修改的 map 对象(但是可以通过直接修改 map 的 value 对象来达到相同的目的)
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public String getParameter(String key) {
        String value = parameters.get(key);
        if (value == null || value.length() == 0) {
            value = parameters.get(Constants.DEFAULT_KEY_PREFIX + key);
        }
        return value;
    }
}
