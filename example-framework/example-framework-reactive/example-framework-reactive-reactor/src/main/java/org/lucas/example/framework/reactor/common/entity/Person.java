package org.lucas.example.framework.reactor.common.entity;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static List<Person> makeList() {
        List<Person> personList = new ArrayList<Person>();
        Person p1 = new Person();
        p1.setAge(10);
        p1.setName("zlx");
        personList.add(p1);

        p1 = new Person();
        p1.setAge(12);
        p1.setName("jiaduo");
        personList.add(p1);

        p1 = new Person();
        p1.setAge(5);
        p1.setName("ruoran");
        personList.add(p1);
        return personList;
    }

}
