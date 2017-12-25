package org.shaw.pattern.observer.method2.impl;

/**
 * @create: 2017-11-07
 * @description:
 */
public interface Observer {
    /**
     * 更新状态
     *
     * @param subject 更新对象
     */
    void update(Subject subject);
}
