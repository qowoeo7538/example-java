package org.shaw.load.dynamic;

/**
 * Created by joy on 17-2-6.
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        //new是对象的静态加载，编译时就会加载;
        OfficeBetter officeBetter = new OfficeBetter();
        OfficeBetter.load("org.shaw.load.dynamic.Word");
    }
}
