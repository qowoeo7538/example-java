package org.lucas.base.path;

import java.io.IOException;

public class PathDemo {
    public static void main(String[] args) throws IOException {
        // 获取路径
        getPath();
        // 根据路径加载资源
        // loadSource();
    }

    /**
     * 1) "." 会自动转成以 "/" 分割路径
     * 2) 如果以 "/" 开头则加载 classpath 下的资源
     * 3) 如果不以 "/" 开头则加载当前目录下的资源
     * 4) 从 classpath 加载资源，且开头不能是 "/" 其后按照 "/" 分割的路径（相对路径）
     */
    private static void getPath() {
        // 获取当前项目路径
        System.out.println(System.getProperty("user.dir"));

        // 获取当前编译文件路径
        System.out.println(PathDemo.class.getResource(""));
        // 获取当前编译目录
        System.out.println(PathDemo.class.getResource("/"));

        // 获取当前编译目录（path开头不能是 "/" 且其后按照 "/" 分割的格式）
        System.out.println(PathDemo.class.getClassLoader().getResource(""));

        // 获取当前项目路径（path开头不能是 "/" 且其后按照 "/" 分割的格式）
        System.out.println(ClassLoader.getSystemResource(""));
    }

    private static void loadSource() {
        // 从classpath下加载资源
        PathDemo.class.getResource("/org/lucas/base/path/PathDemo.class");
        // 从当前目录下加载资源
        PathDemo.class.getResource("PathDemo.class");
        // 从classpath下加载资源
        PathDemo.class.getClassLoader().getResource("org/lucas/base/path/PathDemo.class");
    }
}
