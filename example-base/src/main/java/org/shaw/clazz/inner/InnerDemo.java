package org.shaw.clazz.inner;

import org.junit.Test;
import org.shaw.clazz.inner.impl.ExternalClass;

public class InnerDemo {

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
