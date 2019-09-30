package org.lucas.example.foundation.io.demo.character;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by joy on 17-2-8.
 */
public class FrAndFwDemo {
    public static void main(String[] args) throws Exception {
        copyText("/home/joy/桌面/a.properties", "/home/joy/桌面/dsfsdfsdf.properties");
    }

    /**
     * 文本拷贝（推荐方式，但不能使用编码）
     *
     * @param srcData
     * @param copyData
     * @throws Exception
     */
    public static void copyText(String srcData, String copyData) throws IOException {
        try (FileReader fileReader = new FileReader(srcData);
             //FileWriter fileWriter = new FileWriter(copyData,true); 对文件追加内容
             FileWriter fileWriter = new FileWriter(copyData)) {

            char[] c = new char[8 * 1024];
            int i;
            while ((i = fileReader.read(c, 0, c.length)) != -1) {
                String str = new String(c, 0, i);
                System.out.print(str);
                // 使用i，读到多少字符就写多少字符
                fileWriter.write(c, 0, i);
                fileWriter.flush();
            }
        }
    }
}
