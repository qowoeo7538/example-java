package org.shaw.kata.clazz.inherit.impl;

public class ExtendClass2 extends BaseClass {

    private String attribute = "扩展类2属性";

    @Override
    public void something(){
        System.out.println(attribute);
    }

}
