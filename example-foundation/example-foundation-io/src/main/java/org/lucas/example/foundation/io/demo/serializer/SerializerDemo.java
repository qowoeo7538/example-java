package org.lucas.example.foundation.io.demo.serializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.util.SerializerUtils;
import org.lucas.example.foundation.io.demo.serializer.customizable.Student;
import org.lucas.example.foundation.io.demo.serializer.invalid.User;
import org.lucas.example.foundation.io.demo.serializer.single.Car;

import java.io.IOException;

public class SerializerDemo {

    @Test
    public void demoObjectSerializer() throws IOException, ClassNotFoundException {
        Student student = new Student("id4300", "张三", 22);
        SerializerUtils.javaWriteObject("/home/joy/桌面/object.dat", student);
        Student newStudent = SerializerUtils.javaReadObject("/home/joy/桌面/object.dat");
        Assertions.assertNotNull(newStudent);
    }

    @Test
    public void demoObjectSerializer1() throws IOException, ClassNotFoundException {
        User user = new User("id430","username01","pwd01");
        SerializerUtils.javaWriteObject("/home/joy/桌面/object.dat",user);
        User newUser = SerializerUtils.javaReadObject("/home/joy/桌面/object.dat");
        Assertions.assertNotNull(newUser);
    }

    @Test
    public void demoObjectSerializer2() throws IOException, ClassNotFoundException {
        Car car = Car.getCar();
        SerializerUtils.javaWriteObject("/home/joy/桌面/object.dat", car);
        Car newCar = SerializerUtils.javaReadObject("/home/joy/桌面/object.dat");
        Assertions.assertNotNull(newCar);
    }
}
