package org.shaw.nio.server.impl;

/**
 * @create: 2017-12-21
 * @description: 数据块
 */
public class QueueIntFlip {

    public int[] elements;

    /** 容量大小 */
    public int capacity;

    /** 写入位置 */
    public int writePos = 0;

    /** 读取位置 */
    public int readPos = 0;

    /**
     * 是否读取完毕
     * <p>
     * if (readPos == capacity) return false;
     */
    public boolean flipped = false;

    public QueueIntFlip(int capacity) {
        this.capacity = capacity;

        //todo get from TypeAllocator ?
        this.elements = new int[capacity];
    }

    /**
     * 返回当前读位置数据
     *
     * @return 如果读位置小于写位置则 {@code return elements[readPos++]} 否则 {@code return -1};
     */
    public int take() {
        if (!flipped) {
            /**
             * 当读位置小于写位置返回读取位置的元素数据
             * 否则 return -1
             */
            if (readPos < writePos) {
                return elements[readPos++];
            } else {
                return -1;
            }
        } else {
            if (readPos == capacity) {
                readPos = 0;
                flipped = false;

                if (readPos < writePos) {
                    return elements[readPos++];
                } else {
                    return -1;
                }
            } else {
                return elements[readPos++];
            }
        }
    }

    /**
     * 将 element 插入缓存块。
     *
     * @param element 插入的元素
     * @return 成功 {@code return true}，失败 {@code return false}
     */
    public boolean put(int element) {
        if (!flipped) {
            if (writePos == capacity) {
                writePos = 0;
                flipped = true;

                if (writePos < readPos) {
                    elements[writePos++] = element;
                    return true;
                } else {
                    return false;
                }
            } else {
                elements[writePos++] = element;
                return true;
            }
        } else {
            if (writePos < readPos) {
                elements[writePos++] = element;
                return true;
            } else {
                return false;
            }
        }
    }
}
