package org.shaw.thread.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by joy on 17-3-17.
 */
public class SteamThreadTest {
    public static void main(String[] args) {
        listSteam();
    }

    public static void listSteam(){
        List<String> list = new ArrayList<String>();

        list.add("cbc");
        list.add("dbc");
        list.add("abc");
        list.add("bbc");

        Optional<String> max = list.stream().max(String::compareTo); //通过集合的中间流，把String.compareTo方法传递给中间流操作max。
        System.out.println(max.get());
        System.out.println("==========================");

        list.stream().sorted().forEach(e -> System.out.println(e));
        System.out.println("==========================");
        System.out.println(list.stream().distinct().count());
    }
}
