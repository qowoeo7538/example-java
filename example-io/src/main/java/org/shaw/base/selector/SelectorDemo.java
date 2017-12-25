package org.shaw.base.selector;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * @create: 2017-12-13
 * @description:
 */
public class SelectorDemo {
    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
        }catch (IOException e){

        }
    }
}
