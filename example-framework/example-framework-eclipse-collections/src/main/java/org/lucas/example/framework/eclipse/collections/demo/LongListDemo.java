package org.lucas.example.framework.eclipse.collections.demo;

import org.eclipse.collections.api.list.primitive.LongList;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class LongListDemo {

    @Test
    public void process() {
        {
            Long startTime = System.currentTimeMillis();
            LongList list = LongLists.mutable.withAll(LongStream.range(0, 100));
            list.each(i -> i = +1);
            list.select(i -> i % 3 == 0);
            System.out.println(System.currentTimeMillis() - startTime);
        }
        {
            Long startTime = System.currentTimeMillis();
            List<Long> list = new ArrayList<>();
            LongStream.range(1, 100).forEach(list::add);
            list.stream().forEach(i -> i = +1L);
            list.stream().filter(i -> i % 3 == 0).collect(Collectors.toList());
            System.out.println(System.currentTimeMillis() - startTime);
        }
    }

}
