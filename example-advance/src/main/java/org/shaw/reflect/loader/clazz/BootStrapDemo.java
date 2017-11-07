package org.shaw.reflect.loader.clazz;

import sun.misc.Launcher;

import java.net.URL;

/**
 *
 */
public class BootStrapDemo {
    public static void main(String[] args) {
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
        System.out.println("==========================");
        System.out.println(System.getProperty("sun.boot.class.path"));
    }
}
