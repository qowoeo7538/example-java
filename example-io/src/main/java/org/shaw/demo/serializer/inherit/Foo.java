package org.shaw.demo.serializer.inherit;

import java.io.Serializable;

/**
 * 一个类实现了序列化接口，那么其子类都可以进行序列化
 */
public class Foo implements Serializable {
    public Foo() {
        System.out.println("foo...");
    }
}
