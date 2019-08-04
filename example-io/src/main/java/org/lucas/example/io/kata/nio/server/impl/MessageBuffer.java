package org.lucas.example.io.kata.nio.server.impl;

/**
 * 原理：以块的形式读写数据，可以在面向流的系统中，处理速度也会变快。
 */
public class MessageBuffer {

    /** 1KB = 1024 byte */
    public final static int KB = 1024;
    /** 1MB = 1024 KB */
    public static int MB = 1024 * KB;

    /** 4KB */
    private static final int CAPACITY_SMALL = 4 * KB;
    /** 128KB */
    private static final int CAPACITY_MEDIUM = 128 * KB;
    /** 1M */
    private static final int CAPACITY_LARGE = 1024 * KB;

    /** 4MB 消息容器 */
    byte[] smallMessageBuffer = new byte[1024 * 4 * KB];
    /** 16MB 消息容器 */
    byte[] mediumMessageBuffer = new byte[128 * 128 * KB];
    /** 16MB 消息容器 */
    byte[] largeMessageBuffer = new byte[16 * 1 * MB];

    /** 1KB 容器块 */
    QueueIntFlip smallMessageBufferFreeBlocks = new QueueIntFlip(1024);
    /** 128byte 容器块 */
    QueueIntFlip mediumMessageBufferFreeBlocks = new QueueIntFlip(128);
    /** 16byte 容器块 */
    QueueIntFlip largeMessageBufferFreeBlocks = new QueueIntFlip(16);

    public MessageBuffer() {
        for (int i = 0; i < smallMessageBuffer.length; i += CAPACITY_SMALL) {
            this.smallMessageBufferFreeBlocks.put(i);
        }
        for (int i = 0; i < mediumMessageBuffer.length; i += CAPACITY_MEDIUM) {
            this.mediumMessageBufferFreeBlocks.put(i);
        }
        for (int i = 0; i < largeMessageBuffer.length; i += CAPACITY_LARGE) {
            this.largeMessageBufferFreeBlocks.put(i);
        }
    }

    /**
     * 将 {@code MessageBuffer} 构建成 {@code Message} 返回
     *
     * @see QueueIntFlip#take() 返回当前数组信息
     */
    public Message getMessage() {
        int nextFreeSmallBlock = this.smallMessageBufferFreeBlocks.take();
        if (nextFreeSmallBlock == -1) {
            return null;
        }

        /**
         * 构建 {@code Message}
         */
        Message message = new Message(this);
        message.sharedArray = this.smallMessageBuffer;
        message.capacity = CAPACITY_SMALL;
        message.offset = nextFreeSmallBlock;
        message.length = 0;
        return message;
    }

    /**
     * 扩张消息容量
     *
     * @param message 消息对象
     * @return
     */
    public boolean expandMessage(Message message) {
        if (message.capacity == CAPACITY_SMALL) {
            return moveMessage(message, this.smallMessageBufferFreeBlocks, this.mediumMessageBufferFreeBlocks, this.mediumMessageBuffer, CAPACITY_MEDIUM);
        } else if (message.capacity == CAPACITY_MEDIUM) {
            return moveMessage(message, this.mediumMessageBufferFreeBlocks, this.largeMessageBufferFreeBlocks, this.largeMessageBuffer, CAPACITY_LARGE);
        } else {
            return false;
        }
    }

    /**
     * 将 {@code Message} 可读信息复制到相同的大小的 {@code byte[] dest} 中，
     * 再利用缓存块的的数据重新构建扩张后的 {@code Message}
     *
     * @param message        消息对象
     * @param srcBlockQueue  缓存块
     * @param destBlockQueue 备份缓存块
     * @param dest           备份缓存
     * @param newCapacity    扩张容量
     * @return
     * @see QueueIntFlip#take() 返回当前读位置数据
     */
    private boolean moveMessage(Message message, QueueIntFlip srcBlockQueue, QueueIntFlip destBlockQueue, byte[] dest, int newCapacity) {
        int nextFreeBlock = destBlockQueue.take();
        if (nextFreeBlock == -1) {
            return false;
        }
        /**
         * 将消息数据（偏移位置--消息总长度）复制到 16MB（destBlockQueue当前位置的数据开始） 消息缓存中
         *
         * @description: 数组之间的复制
         * @param sharedArray   消息数据（源数组）
         * @param offset        消息数据的偏移位置（源数组要复制的起始位置）
         * @param dest          16MB 消息缓存（目的数组）
         * @param nextFreeBlock 128byte 缓存块当前读位置的数据（目的数组放置的起始位置）
         * @param length        复制的长度
         */
        System.arraycopy(message.sharedArray, message.offset, dest, nextFreeBlock, message.length);
        // 将消息偏移位置放入 1KB 缓存块
        srcBlockQueue.put(message.offset);

        // 重新构建 Message 对象
        message.sharedArray = dest;
        message.offset = nextFreeBlock;
        message.capacity = newCapacity;
        return true;
    }

}
