package org.lucas.uuid;

/**
 * @create: 2017-11-07
 * @description:
 */
public interface IdGenerator {
    /**
     * 获取下一个ID
     *
     * @return {@code long} ID
     */
    long nextId();
}
