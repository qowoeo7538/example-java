package org.lucas.example.foundation.basic.kata.generics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.entity.Person;
import org.lucas.example.common.pojo.entity.SchoolChild;
import org.lucas.example.common.pojo.entity.Student;
import org.lucas.example.common.pojo.entity.Teacher;
import org.lucas.example.foundation.basic.kata.generics.support.GenericTest;

import java.util.ArrayList;
import java.util.List;

public class GenericDemo {

    @Test
    public void genericTest() {
        Student<String> object = new Student<>();
        object.setId("ID430");
        object.setAge(17);
        GenericTest.getMessage(object);

        Student<Integer> student = new Student<>();
        student.setId(430);
        student.setAge(23);
        GenericTest.getMessage(student);
    }

    /**
     * 泛型类型会在编译后会进行类型擦除
     * 编译器用类型参数的擦除替换类型参数。对于无限制类型参数，它的擦除后是 Object。对于上限类型参数，它的擦除后是其上限。
     * 对于具有多个限制的类型参数，使用其最左限制的擦除。
     */
    @Test
    public void abrasion() {

        List<? extends Person> extends1 = new ArrayList<>();
        List<? extends Person> extends2 = new ArrayList<>();
        Assertions.assertEquals(extends1.getClass(), extends2.getClass());

        List<? super Student> super1 = new ArrayList<>();
        List<? super Teacher> super2 = new ArrayList<>();
        Assertions.assertEquals(super1.getClass(), super2.getClass());

        Assertions.assertEquals(extends1.getClass(), super1.getClass());
    }

    /**
     * 创建泛型数组
     */
    @Test
    public void genericArrayTest() {
        Assertions.assertEquals(10, GenericTest.createArray("", 10).length);
    }

    /**
     * <? extends T> 表示类型的上界，表示参数化类型的可能是T或是T的子类，就可以调用T类型的方法。
     * <? extends T & S> 后面的第一个边界，可以为类或接口，之后的均为接口。
     * <p>
     * <? super T> 表示类型下界（Java Core中叫超类型限定），表示参数化类型是此类型的超类型（父类型），直至Object.
     * <p>
     * PECS原则：
     * 如果要从集合中读取类型T的数据，并且不能写入，可以使用 ? extends 通配符；(Producer Extends)
     * 如果要从集合中写入类型T的数据，并且不需要读取，可以使用 ? super 通配符；(Consumer Super)
     * 如果既要存又要取，那么就不要使用任何通配符。
     */
    @Test
    public void pecsTest() {
        {
            // List<? extends Person> 可以解读为，“具有任何从 Person 继承的类型”，意味着，它没有指定具体类型。
            List<? extends Person> list = new ArrayList<>();
            // list.add(new Student()); 不能编译。
            // null 可以代表任意类型，所以可以添加。
            list.add(null);

            // 参数是Object，不涉及任何的通配符，所以编译器允许调用。
            list.contains(new Student<>());
            list.indexOf(new Student());

            // 当元素在此 List 中存在时，编译器能够确定他是Person的子类，所以能够安全获得
            Assertions.assertNull(list.get(0));
        }

        {
            /**
             * List <? super Student>， 代表容器内存放的是 Student 的所有父类，
             * 所以有多态和上转型，这个容器时可以接受所有 Student 父类的子类的。（多态的定义：父类可以接受子类型对象）
             * Student 和 Student1都直接或间接继承了 Child，所以 Student 和 Student1 是能够加入List < ? super Student>这个容器的。
             *
             * list.add(new Child())不能添加，正是因为第1点解释的，容器内存放的是 Student 的所有父类，
             * 正是因为能存放所有， Student 的父类可能有 Child1， Child2， Child3 ， 所以编译器根本不能识别你要存放哪个 Student 的父类，
             * 因为这不能保证类型安全的原则。这从最后的 Object student1 = list.get(0) 可以看出。
             */

            // List<? super Student>表示“具有Student的父类的列表”
            List<? super Student> list = new ArrayList<>();
            list.add(new SchoolChild());
            list.add(new Student());
            // 因为?代表Student的父类，但是编译器不知道你要添加哪种 Student 的父类，因此不能安全地添加。
            // list.add(new Child());

            // 对于 super，get方法返回的是Object，因为编译器不能确定列表中的是Student的哪个子类，所以只能返回Object
            Object student1 = list.get(0);
            Assertions.assertNotNull(student1);
        }
    }

}

