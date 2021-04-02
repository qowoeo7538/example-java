package org.lucas.example.foundation.jvm.demo.load;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.jvm.demo.load.support.OfficeBetter;
import org.lucas.example.foundation.jvm.demo.load.support.Word;

public class ClassLoadDemo {

    /**
     * 动态加载
     *
     * @throws Exception
     */
    @Test
    public void dynamicLoad() throws Exception {
        // new是对象的静态加载，编译时就会加载;
        OfficeBetter officeBetter = new OfficeBetter();
        OfficeBetter.load(Word.class.getName());
    }

}
