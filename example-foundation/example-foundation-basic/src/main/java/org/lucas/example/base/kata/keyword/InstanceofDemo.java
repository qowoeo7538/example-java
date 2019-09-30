package org.lucas.example.base.kata.keyword;

import org.lucas.example.base.kata.keyword.impl.Animal;
import org.lucas.example.base.kata.keyword.impl.Cat;

/**
 * 是否是子类及本类
 */
public class InstanceofDemo {

    private static Animal animal = new Animal();
    private static Animal animalCat = new Cat();
    private static Cat cat = new Cat();

    public static void main(String[] args) {
        instanceofTest();
        isInstanceTest();
    }

    /**
     * instanceof: 判断左侧是否是右侧的对象本身或子类的实例.
     * null 直接返回 false.
     */
    private static void instanceofTest(){
        System.out.println("=========== instanceof ===========");
        System.out.println(animal instanceof Cat);
        System.out.println(animalCat instanceof Cat);
        System.out.println(cat instanceof Animal);
        System.out.println(null instanceof Animal);
    }

    /**
     * ClassType.isInstance(obj)：判断 obj 是否能否转 ClassType 类型。
     * null 直接返回 false.
     */
    private static void isInstanceTest(){
        System.out.println("=========== isInstance() ===========");
        System.out.println(Animal.class.isInstance(new Cat()));
        System.out.println(Cat.class.isInstance(new Animal()));
        System.out.println(Cat.class.isInstance(null));
    }

    private static  void inInstanceTest(){
        System.out.println("=========== inInstance() ===========");
    }
}
