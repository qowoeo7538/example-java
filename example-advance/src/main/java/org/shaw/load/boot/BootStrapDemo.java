package org.shaw.load.boot;

import sun.misc.Launcher;

import java.net.URL;

/**
 *
 */
public class BootStrapDemo {
    public static void main(String[] args) {
        // 读取引导类加载器加载路径,跟扩展类加载器加载路径
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
        System.out.println("==========================");
        System.out.println(System.getProperty("sun.boot.class.path"));
    }
}
