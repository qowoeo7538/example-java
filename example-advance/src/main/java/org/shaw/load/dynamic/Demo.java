package org.shaw.load.dynamic;

import org.shaw.load.dynamic.impl.OfficeBetter;
import org.shaw.load.dynamic.impl.OfficeBetter;

/**
 * Created by joy on 17-2-6.
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        //new是对象的静态加载，编译时就会加载;
        OfficeBetter officeBetter = new OfficeBetter();
        OfficeBetter.load("org.shaw.load.dynamic.impl.Word");
    }
}
