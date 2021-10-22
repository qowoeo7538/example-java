package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;

class UnsafeClassDemo {

    /**
     * 告诉JVM定义一个类，返回类实例，此方法会跳过JVM的所有安全检查。
     * 默认情况下，ClassLoader(类加载器)和ProtectionDomain(保护域)实例应该来源于调用者。
     */
    @Test
    void demoDefineClass() {

    }

    /**
     * 1、VM Anonymous Class可以看作一种模板机制，如果程序要动态生成很多结构相同、只是若干变量不同的类的话，可以先创建出一个包含占位符常量的正常类作为模板，
     * 然后利用sun.misc.Unsafe#defineAnonymousClass()方法，传入该类(host class，宿主类或者模板类)以及一个作为"constant pool path"的数组来替换指定的常量为任意值，
     * 结果得到的就是一个替换了常量的VM Anonymous Class。
     * 2、VM Anonymous Class从VM的角度看是真正的"没有名字"的，在构造出来之后只能通过Unsafe#defineAnonymousClass()返回出来一个Class实例来进行反射操作。
     * 3. VM anonymous class可以指定一个host class，以便拥有跟host class一样的权限（可见性等）；
     * 4.1 VM anonymous+class不显式挂在任何ClassLoader下面。它可以很方便地被GC回收：只要该类没有任何活着的实例，
     * 并且没有任何活着的强引用指向代表该类的 java.lang.Class 对象，则该类可以被回收；
     * 4.2 而一般的类则需要牵扯到更多东西，例如说要等类的ClassLoader（defining class loader）以及该ClassLoader所加载的所有类都死了才可以回收。
     * 5 VM anonymous class 与 Java语言层面的匿名内部类（anonymous inner class）是完全不同、不相关的概念。后者只是在Java语言层面“没有名字”而已，
     * 对VM而言它还是完全正常、普通、有名字的类（名字是由Java源码编译器（例如javac）构造出来的）。
     */
    @Test
    void demoDefineAnonymousClass() {

    }

    /**
     * 检测给定的类是否已经初始化。通常需要使用在获取一个类的静态属性的时候(因为一个类如果没初始化，它的静态属性也不会初始化)。
     */
    @Test
    void demoEnsureClassInitialized() {

    }

    /**
     * 检测给定的类是否需要初始化。通常需要使用在获取一个类的静态属性的时候(因为一个类如果没初始化，它的静态属性也不会初始化)。
     * 此方法当且仅当ensureClassInitialized方法不生效的时候才返回false。
     */
    @Test
    void demoShouldBeInitialized() {

    }

    /**
     * 通过Class对象创建一个类的实例，不需要调用其构造函数、初始化代码、JVM安全检查等等。同时，它抑制修饰符检测，也就是即使构造器是private修饰的也能通过此方法实例化。
     */
    @Test
    void demoAllocateInstance() {

    }

}
