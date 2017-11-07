package org.shaw.advance.reflect.loader.clazz;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * class loader 是一个负责加载 classes 的对象
 * ClassLoader 类是一个抽象类，需要给出类的二进制名称，class loader 尝试定位或者产生一个 class 的数据,一个典型的策略是把二进制名字转换成文件名然后到文件系统中找到该文件。
 * 通过继承java.lang.ClassLoader类的方式实现自己的类加载器，以满足一些特殊的需求。
 * 系统提供的类加载器来说，系统类加载器的父类加载器是扩展类加载器，而扩展类加载器的父类加载器是引导类加载器；对于开发人员编写的类加载器来说，其父类加载器是加载此类加载器 Java 类的类加载器。
 * 一般来说，开发人员编写的类加载器的父类加载器是系统类加载器。类加载器通过这种方式组织起来，形成树状结构。
 * 树的根节点就是引导类加载器。
 *
 * loadClass方法的实现:
 *      使用指定的二进制名称来加载类，这个方法的默认实现按照以下顺序查找类：
 *      调用findLoadedClass(String) 方法检查这个类是否被加载过,
 *      使用父加载器调用 loadClass(String) 方法，如果父加载器为 Null，
 *      类加载器装载虚拟机内置的加载器调用 findClass(String) 方法装载类，
 *      如果，按照以上的步骤成功的找到对应的类，并且该方法接收的 resolve 参数的值为 true,
 *      那么就调用resolveClass(Class) 方法来处理类。 ClassLoader 的子类最好覆盖 findClass(String) 而不是这个方法。
 *      除非被重写，这个方法默认在整个装载过程中都是同步的（线程安全的）。
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        // 验证类加载器与类加载器间的父子关系
        classLoaderRelation();

        // 使用ClassLoaderTest的类加载器加载本类
        Object obj1 = ClassLoaderTest.class.getClassLoader().loadClass("com.myweb.reflect.classloader.ClassLoaderTest").newInstance();
        System.out.println("默认类加载器："+obj1.getClass().getClassLoader());

        // 自定义类加载器加载
        MyClassLoader myClassLoader = new MyClassLoader("/home/joy/桌面/");
        Class<?> c = Class.forName("com.myweb.annotation.User",true,myClassLoader);
        Object object = c.newInstance();
        System.out.println(object.getClass());
        System.out.println(object.getClass().getClassLoader());
    }
    /**
     * 验证类加载器与类加载器间的父子关系
     */
    public static void classLoaderRelation (){
        // 获取系统/应用类的加载器
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统/应用类加载器："+appClassLoader);

        //获取系统/应用类加载器的父类加载器，得到扩展加载器
        ClassLoader extcClassLoader = appClassLoader.getParent();
        System.out.println("扩展类加载器："+extcClassLoader);
        System.out.println("扩展类加载器的加载路径："+System.getProperty("java.ext.dirs"));

        //获取扩展类加载器的父类加载器，但因根类加载器并不用JAVA实现的所以不能获取
        System.out.println("扩展类的父类加载器："+extcClassLoader.getParent());
    }
}

// 自定义类加载器加载类
class MyClassLoader extends ClassLoader{
    public String _filePath;

    public String _name;

    public MyClassLoader(){}

    public MyClassLoader(ClassLoader parent){
        super(parent);
    }

    public MyClassLoader(String filePath){
        this._filePath = filePath;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        _name = name;
        File file = getClassFile();
        try {
            byte[] bytes = getClassToBytes(file);
            Class<?> c = this.defineClass(name,bytes,0,bytes.length);
            return c;
        }catch (Exception e){
            return super.loadClass(name);
        }
    }

    private File getClassFile(){
        String fileName = _filePath+_name.substring(_name.lastIndexOf(".")+1)+".class";
        return new File(fileName);
    }

    // 将Class文件转换成byte数组
    public byte[] getClassToBytes(File file) throws IOException{
        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            WritableByteChannel wbc = Channels.newChannel(baos);
            ByteBuffer by = ByteBuffer.allocate(1024);

            while (true){
                int i = fc.read(by);
                if(i == 0 || i == -1) break;
                by.flip();
                wbc.write(by);
                by.clear();
            }
            fc.close();
            fis.close();
            wbc.close();
            baos.close();
            return baos.toByteArray();
        }catch (IOException e){
            throw new IOException();
        }

    }

    public void set_filePath(String filePath) {
        this._filePath = filePath;
    }
}
