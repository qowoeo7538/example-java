package org.lucas.demo.buffer;

import org.junit.Test;
import org.lucas.demo.buffer.impl.CopyFile;

import java.io.IOException;

/**
 * 或读取操作时，都会加上缓冲，这种流模式提高了IO的性能
 * 从应用程序中把输入放入文件，相当于将一缸水倒入到另一个缸中:
 * FileOutputStream--->write()方法相当于一滴一滴地把水“转移”过去
 * DataOutputStream-->writeXxx()方法会方便一些，相当于一瓢一瓢把水“转移”过去
 * BufferedOutputStream--->write方法更方便，相当于一飘一瓢先放入桶中，再从桶中倒入到另一个缸中，性能提高了
 * <p>
 * Created by joy on 17-2-7.
 */
public class StreamBufferDemo {

    private final static String SOURCE = "/home/lucas/桌面/in.sh";

    private final static String TARGET = "/home/shaw/桌面/wwwwwww.sh";

    /**
     * 利用缓冲区拷贝文件
     */
    @Test
    public void copyFileByBuffer() throws IOException {
        CopyFile.copyFileByBuffer(SOURCE, TARGET);
    }
}
