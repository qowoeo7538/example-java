package org.lucas.example.foundation.basic.demo.clazz;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.basic.demo.clazz.support.BaseClass;
import org.lucas.example.foundation.basic.demo.clazz.support.Children;
import org.lucas.example.foundation.basic.demo.clazz.support.ExtendClass1;
import org.lucas.example.foundation.basic.demo.clazz.support.ExtendClass2;
import org.lucas.example.foundation.basic.demo.clazz.support.ExternalClass;
import org.lucas.example.foundation.basic.demo.clazz.support.Parent;

public class ClassDemo {

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

        var extendClass = new ExtendClass1();
        extendClass.something();

        var extendClass2 = new ExtendClass2();
        extendClass2.something();
    }

    /**
     * 继承静态属性是否会被覆盖
     */
    @Test
    public void demoInheritStatic() {
        {
            var base = new BaseClass();
            base.COUNT.addAndGet(10);
            System.out.println(base.COUNT.get());

            var extend = new ExtendClass1();
            System.out.println(extend.COUNT.get());

            var extend2 = new ExtendClass2();
            System.out.println(extend2.COUNT.get());
        }
        System.out.println("==========================");
        {
            var extend = new ExtendClass1();
            extend.COUNT.addAndGet(10);
            System.out.println(extend.COUNT.get());

            var base = new BaseClass();
            System.out.println(base.COUNT.get());

            var extend2 = new ExtendClass2();
            System.out.println(extend2.COUNT.get());
        }
    }

    /**
     * 继承静态属性是否会被覆盖
     */
    @Test
    public void demoInheritStaticClass() {
        {
            var base = new BaseClass();
            base.getIndex().INDEX.addAndGet(10);
            System.out.println(base.getIndex().INDEX.get());

            var extend = new ExtendClass1();
            System.out.println(extend.getIndex().INDEX.get());

            var extend2 = new ExtendClass2();
            System.out.println(extend2.getIndex().INDEX.get());
        }
        System.out.println("==========================");
        {
            var extend = new ExtendClass1();
            extend.getIndex().INDEX.addAndGet(10);
            System.out.println(extend.getIndex().INDEX.get());

            var base = new BaseClass();
            System.out.println(base.getIndex().INDEX.get());

            var extend2 = new ExtendClass2();
            System.out.println(extend2.getIndex().INDEX.get());
        }
    }

    /**
     * 内部类调用外部属性
     */
    @Test
    public void demoField() {
        var externalClass = new ExternalClass();
        var innerClass = externalClass.new InnerClass();
        innerClass.print();
    }

    /**
     * 静态内部类创建
     */
    @Test
    public void demoStatic() {
        var staticClass = new ExternalClass.StaticClass();
        staticClass.print();
    }


}
