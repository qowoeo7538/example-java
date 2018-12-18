package org.shaw.kata.clazz.inherit;

import org.junit.Test;
import org.shaw.kata.clazz.inherit.impl.BaseClass;
import org.shaw.kata.clazz.inherit.impl.ExtendClass1;
import org.shaw.kata.clazz.inherit.impl.ExtendClass2;

/**
 * @create: 2017-11-15
 * @description:
 */
public class InheritDemo {

    @Test
    public void inheritTest() {
        BaseClass class1 = new ExtendClass1();
        class1.something();

        BaseClass class2 = new ExtendClass2();
        class2.something();

        ExtendClass1 extendClass = new ExtendClass1();
        extendClass.something();

        ExtendClass2 extendClass2 = new ExtendClass2();
        extendClass2.something();
    }
}
