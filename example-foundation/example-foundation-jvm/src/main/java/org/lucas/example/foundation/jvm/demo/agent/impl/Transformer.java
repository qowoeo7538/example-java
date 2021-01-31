package org.lucas.example.foundation.jvm.demo.agent.impl;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 基于 load 的方式来实现 Instrument，介于 ClassLoader 每加
 * 载一个目标类就会调用 transform() 函数
 */
public class Transformer implements ClassFileTransformer {

    private static final String PATH_SEPARATOR = "/";

    private static final String PACKAGE_SEPARATOR = ".";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return enhancement(className);
    }

    public byte[] enhancement(String className) {
        // 1 判断目标类型,如果是 String 类型。
        if ((className = className.replaceAll(PATH_SEPARATOR, PACKAGE_SEPARATOR)).equals("java.lang.String")) {
            try {
                // 1.1 加载 .class 文件
                var cls = ClassPool.getDefault().get(className);
                // 1.2 获取增强方法
                var method = cls.getDeclaredMethod("toString");
                // 1.3 增强逻辑
                method.insertBefore("System.out.println(\"before\");");
                method.insertAfter("System.out.println(\"after\");");
                // 1.4 转换字节码
                return cls.toBytecode();
            } catch (NotFoundException | CannotCompileException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
