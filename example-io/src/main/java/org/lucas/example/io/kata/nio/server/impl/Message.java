package org.lucas.example.io.kata.nio.server.impl;

/**
 * @create: 2017-12-21
 * @description: 消息体描述
 */
public class Message {
    public long socketId = 0;

    /** 消息数据 */
    public byte[] sharedArray;

    /** 偏移位置 */
    public int offset = 0;

    /** 容量 */
    public int capacity = 0;

    /** 分配的部分使用的字节数 */
    public int length = 0;

    public MessageBuffer messageBuffer;

    public Message(MessageBuffer messageBuffer) {
        this.messageBuffer = messageBuffer;
    }

    /**
     * @param byteArray 字节数组
     * @return
     * @see #writeToMessage(byte[], int, int)
     */
    public int writeToMessage(byte[] byteArray) {
        return writeToMessage(byteArray, 0, byteArray.length);
    }

    public int writeToMessage(byte[] byteArray, int offset, int length) {
        int remaining = length;
        while (this.length + remaining > capacity) {
            if (!this.messageBuffer.expandMessage(this)) {
                return -1;
            }
        }
        int bytesToCopy = Math.min(remaining, this.capacity - this.length);
        System.arraycopy(byteArray, offset, this.sharedArray, this.offset + this.length, bytesToCopy);
        this.length += bytesToCopy;
        return bytesToCopy;
    }
}
