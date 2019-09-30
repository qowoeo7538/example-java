package org.lucas.example.foundation.io.demo.bytes;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.io.demo.bytes.impl.EncodeTest;
import org.lucas.example.foundation.io.demo.bytes.impl.CharsetName;

/**
 * Created by joy on 17-2-2.
 */
public class EncodeDemo {

    /**
     * String编码转换
     *
     * @throws Exception
     */
    @Test
    public void encode() throws Exception {
        // 使用系统默认编码
        EncodeTest.encode("为为为为", null);
        // 使用指定编码
        EncodeTest.encode("四方斯蒂芬", CharsetName.UTF_16BE);
    }

    /**
     * 解码成字节
     */
    @Test
    public void decode() throws Exception {
        String str = "学习JAVA";
        // 使用系统默认编码
        EncodeTest.decode(str, null);
        // 使用指定编码
        EncodeTest.decode(str, CharsetName.UTF_16BE);
    }

}
