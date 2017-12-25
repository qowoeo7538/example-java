package org.shaw.nio.server;

import org.junit.Assert;
import org.junit.Test;

/**
 * @create: 2017-12-22
 * @description:
 */
public class MessageTest {

    @Test
    public void testWriteToMessage() throws Exception {
        MessageBuffer messageBuffer = new MessageBuffer();
        Message message = messageBuffer.getMessage();

        /**
         * 初始化
         */
        byte[] smallSharedArray = message.sharedArray;
        Assert.assertNotNull(message);
        Assert.assertEquals(0, message.offset);
        Assert.assertEquals(0, message.length);
        Assert.assertEquals(4 * 1024, message.capacity);

        /**
         * 第一次扩张
         */
        messageBuffer.expandMessage(message);
        Assert.assertEquals(0, message.offset);
        Assert.assertEquals(0, message.length);
        Assert.assertEquals(128 * 1024, message.capacity);

        byte[] mediumSharedArray = message.sharedArray;
        Assert.assertNotSame(smallSharedArray, mediumSharedArray);

       /* *//**
         * 第二次扩张
         *//*
        messageBuffer.expandMessage(message);
        Assert.assertEquals(0, message.offset);
        Assert.assertEquals(0, message.length);
        Assert.assertEquals(1024 * 1024, message.capacity);

        byte[] largeSharedArray = message.sharedArray;
        Assert.assertNotSame(smallSharedArray, largeSharedArray);
        Assert.assertNotSame(mediumSharedArray, largeSharedArray);

        *//**
         * 第三次扩张
         *//*
        Assert.assertFalse(messageBuffer.expandMessage(message));
        Assert.assertEquals(0, message.offset);
        Assert.assertEquals(0, message.length);
        Assert.assertEquals(1024 * 1024, message.capacity);
        Assert.assertSame(message.sharedArray, largeSharedArray);*/
    }
}
