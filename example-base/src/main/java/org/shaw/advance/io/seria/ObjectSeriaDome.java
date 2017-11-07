package org.shaw.advance.io.seria;

import java.io.*;

/**
 * Created by joy on 17-2-8.
 */
public class ObjectSeriaDome {
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

/*
 *   一个类实现了序列化接口，那么其子类都可以进行序列化
 */
class Foo implements Serializable {
    public Foo() {
        System.out.println("foo...");
    }
}

class Foo1 extends Foo {
    public Foo1() {
        System.out.println("foo1...");
    }
}

class Foo2 extends Foo1 {
    public Foo2() {
        System.out.println("foo2...");
    }
}

class Bar {
    public Bar() {
        System.out.println("bar");
    }
}

class Bar1 extends Bar {
    public Bar1() {
        System.out.println("bar1..");
    }
}

class Bar2 extends Bar1 implements Serializable {
    public Bar2() {
        System.out.println("bar2...");
    }
}