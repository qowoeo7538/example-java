package org.shaw.keyword;

import org.shaw.keyword.impl.Animal;
import org.shaw.keyword.impl.Cat;

/**
 * 是否是子类及本类
 */
public class InstanceofDemo {
    public static void main(String[] args) {
        Animal animal = new Animal();
        Animal animalCat = new Cat();
        Cat cat = new Cat();
        System.out.println(animal instanceof Cat);
        System.out.println(animalCat instanceof Cat);
        System.out.println(cat instanceof Animal);
    }
}
