package org.lucas.example.foundation.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class SerializerUtils {

    /**
     * 对象序列化(继承类，只要一个类实现序列化接口，将会递归全部序列化)
     */
    public static <T> void javaWriteObject(String fileName, T t) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeObject(t);
            objectOutputStream.flush();
        }
    }

    /**
     * 反序列化
     * <p>
     * 对子类对象进行反序列化操作时，如果其父类没有实现序列化接口,那么其父类的构造函数会被调用
     */
    public static <T> T javaReadObject(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (T) objectInputStream.readObject();
        }
    }

}
