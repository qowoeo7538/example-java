package org.lucas.example.persistence.mysql.common.util.genbean;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanBuild {

    /**
     * 类名
     */
    private String className;

    /**
     * 所继承的父类,默认为 java.lang.Object.
     */
    private String superClassName = "java.lang.Object";

    /**
     * 类包
     */
    private List<String> imports = new ArrayList<>();

    /**
     * 构造方法
     */
    private List<String> constructors = new ArrayList<>();

    /**
     * 字段属性
     */
    private List<String> fields = new ArrayList<>();

    /**
     * 方法
     */
    private List<String> methods = new ArrayList<>();


    public CtClass build(ClassLoader classLoader) throws NotFoundException, CannotCompileException {
        ClassPool pool = new ClassPool(true);
        pool.appendClassPath(new LoaderClassPath(classLoader));

        // 创建类及父类
        CtClass ctClass = pool.makeClass(className, pool.get(superClassName));

        // 添加导入包
        imports.forEach(pool::importPackage);

        // 添加构造方法
        for (String constructor : constructors) {
            ctClass.addConstructor(CtNewConstructor.make(constructor, ctClass));
        }

        // 添加属性
        for (String field : fields) {
            ctClass.addField(CtField.make(field, ctClass));
        }

        // 添加方法
        for (String method : methods) {
            ctClass.addMethod(CtNewMethod.make(method, ctClass));
        }
        return ctClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public List<String> getConstructors() {
        return constructors;
    }

    public void setConstructors(List<String> constructors) {
        this.constructors = constructors;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }
}
