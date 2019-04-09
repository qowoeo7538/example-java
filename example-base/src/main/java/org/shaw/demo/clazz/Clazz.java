package org.shaw.demo.clazz;

import org.junit.Test;
import org.shaw.demo.clazz.impl.BaseClass;
import org.shaw.demo.clazz.impl.Children;
import org.shaw.demo.clazz.impl.ExtendClass1;
import org.shaw.demo.clazz.impl.ExtendClass2;
import org.shaw.demo.clazz.impl.ExternalClass;
import org.shaw.demo.clazz.impl.Parent;

public class Clazz {

    /**
     * 类初始化顺序
     */
    @Test
    public void classExecuteOrder() {
        Parent p = new Children();
    }

    /**
     * 继承属性是否会被覆盖
     */
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

    /**
     * 内部类调用外部属性
     */
    @Test
    public void fieldTest() {
        ExternalClass externalClass = new ExternalClass();
        ExternalClass.InnerClass innerClass = externalClass.new InnerClass();
        innerClass.print();
    }

    /**
     * 静态内部类创建
     */
    @Test
    public void staticTest() {
        ExternalClass.StaticClass staticClass = new ExternalClass.StaticClass();
        staticClass.print();
    }
}
