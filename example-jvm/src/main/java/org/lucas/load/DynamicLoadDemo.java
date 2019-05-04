package org.lucas.load;


import org.junit.Test;
import org.lucas.load.impl.OfficeBetter;

/**
 * Created by joy on 17-2-6.
 */
public class DynamicLoadDemo {

    /**
     * 动态加载
     *
     * @throws Exception
     */
    @Test
    public void dynamicLoad() throws Exception {
        // new是对象的静态加载，编译时就会加载;
        OfficeBetter officeBetter = new OfficeBetter();
        OfficeBetter.load("org.lucas.load.dynamic.impl.Word");
    }

}
