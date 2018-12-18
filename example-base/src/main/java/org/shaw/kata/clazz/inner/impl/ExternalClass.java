package org.shaw.kata.clazz.inner.impl;

public class ExternalClass {

    public String externalField = "external";

     public class InnerClass{

        public void print(){
            System.out.println(ExternalClass.this.externalField);
        }
    }

    public static class StaticClass{

        public void print(){
            System.out.println("StaticClass");
        }

    }

}
