package org.lucas.example.framework.eclipse.collections.demo;

import org.eclipse.collections.api.list.primitive.LongList;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

public class LongListDemo {

    @Test
    public void process() {
        Long startTime = System.currentTimeMillis();
        LongList list = LongLists.mutable.withAll(LongStream.range(0, 1000_0000));
        list.each(i -> i = +1);
        list.select(i -> i % 3 == 0);
        System.out.println(System.currentTimeMillis() - startTime);
    }

}
