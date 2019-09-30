package org.lucas.example.foundation.basic.kata.generic.impl;

/**
 * 集合的泛型，编译之后泛型将不存在，可以通过反射绕过编译。
 */
public class Student<T, U> extends Child implements Person<String> {

    private T id;

    private U age;

    public Student() {
    }

    public Student(T id, U age) {
        this.id = id;
        this.age = age;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public U getAge() {
        return age;
    }

    public void setAge(U y) {
        this.age = age;
    }

    @Override
    public String getMessage() {
        return "正在学习";
    }
}

