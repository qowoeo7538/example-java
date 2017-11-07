package org.shaw.io.seria.inherit;

import org.shaw.io.seria.single.Car;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @create: 2017-11-07
 * @description:
 */
public class InheritSeriaDemo {

    public static void main(String[] args) throws Exception {
        /*Student student = new Student("id4300","张三",22);
        objectSeria("/home/joy/桌面/object.dat",student);
        Student student  = objectDeseria("/home/joy/桌面/object.dat");
        System.out.println(student);*/

        /*User user = new User("id430","username01","pwd01");
        objectSeria("/home/joy/桌面/object.dat",user);
        User user = objectDeseria("/home/joy/桌面/object.dat");
        System.out.println(user);*/

        Car car = Car.getCar();
        objectSeria("/home/joy/桌面/object.dat", car);
        Car newCar = objectDeseria("/home/joy/桌面/object.dat");
        System.out.println(newCar);
        System.out.println(newCar == car);
    }

    /**
     * 对象序列化(继承类，只要一个类实现序列化接口，将会递归全部序列化)
     */
    public static <T> void objectSeria(String fileName, T t) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        objectOutputStream.writeObject(t);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    /**
     * 反序列化
     * <p>
     * 对子类对象进行反序列化操作时，如果其父类没有实现序列化接口,那么其父类的构造函数会被调用
     */
    public static <T> T objectDeseria(String fileName) throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
        T obj = (T) objectInputStream.readObject();
        objectInputStream.close();
        return obj;
    }
}
