package org.shaw.nio.server.impl;

import org.junit.Assert;
import org.junit.Test;
import org.shaw.nio.server.impl.Message;
import org.shaw.nio.server.impl.MessageBuffer;

/**
 * @create: 2017-12-23
 * @description:
 */
public class MessageBufferTest {

    @Test
    public void testExpandMessage() {

        MessageBuffer messageBuffer = new MessageBuffer();

        Message message = messageBuffer.getMessage();

        byte[] smallSharedArray = message.sharedArray;

        // 初始化容量
        Assert.assertNotNull(message);
        Assert.assertEquals(0, message.offset);
        Assert.assertEquals(0, message.length);
        Assert.assertEquals(4 * 1024, message.capacity);

        // 第一次扩张
        /*messageBuffer.expandMessage(message);
        Assert.assertEquals(0, message.offset);
        Assert.assertEquals(0, message.length);
        Assert.assertEquals(128 * 1024, message.capacity);

        byte[] mediumSharedArray = message.sharedArray;
        Assert.assertNotSame(smallSharedArray, mediumSharedArray);

        // 第二次扩张
        messageBuffer.expandMessage(message);
        Assert.assertEquals(0, message.offset);
        Assert.assertEquals(0, message.length);
        Assert.assertEquals(1024 * 1024, message.capacity);

        byte[] largeSharedArray = message.sharedArray;
        Assert.assertNotSame(smallSharedArray, largeSharedArray);
        Assert.assertNotSame(mediumSharedArray, largeSharedArray);

        // 无效扩张
        Assert.assertFalse(messageBuffer.expandMessage(message));
        Assert.assertEquals(0, message.offset);
        Assert.assertEquals(0, message.length);
        Assert.assertEquals(1024 * 1024, message.capacity);
        Assert.assertSame(message.sharedArray, largeSharedArray);*/
    }
}
