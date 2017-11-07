package org.shaw.io.file;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by joy on 17-2-1.
 */
public class PropertiesIO {
    public static void main(String[] args){
        try {
            //读取属性文件
            /*FileInputStream fileInputStream = new FileInputStream("/home/joy/桌面/a.properties");
            Properties propertiesInput = propertiesInputTest(fileInputStream);*/

            //向属性文件输入
            FileOutputStream fileOutputStream =new FileOutputStream("/home/joy/桌面/a.properties",true);
            Properties properties =new Properties();
            properties.setProperty("键","值");
            String annotation = "注释";
            propertiesOutTest(fileOutputStream,properties,annotation,"utf-8");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 读取属性文件
     * @param fileInputStream
     */
    public static Properties propertiesInputTest(FileInputStream fileInputStream)throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = new BufferedInputStream(fileInputStream);
        properties.load(inputStream);
        Iterator<String> iterator = properties.stringPropertyNames().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key + ":" + properties.getProperty(key));
        }
        inputStream.close();
        return properties;
    }

    /**
     * 对属性文件输出
     * @param fileOutputStream
     * @return
     * @throws Exception
     */
    public static void propertiesOutTest(OutputStream fileOutputStream , Properties properties, String annotation,String encode)throws Exception{
        //含中文需要对字节流包装;
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream,encode));
        properties.store(bufferedWriter,annotation);
        bufferedWriter.close();
    }
}
