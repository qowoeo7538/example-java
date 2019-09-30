package org.lucas.example.foundation.reflection.demo.info.clazz.impl;

import java.lang.annotation.*;

//阐述了某个被标注的类的子类继承该注解
@Inherited

//注解作用范围
@Target({ElementType.TYPE})

// 注解保留范围
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    //如果只有一个成员属性，必须声明为value();
    String value();
}

