package org.lucas.example.framework.spring.demo.beans.inject;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.spring.demo.beans.inject.support.AnnotationBeanConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 注解 @Import 可以用于导入第三方包,把实例加入 spring 的 IOC 容器中
 * 使用 @bean 注解
 */
@SpringJUnitConfig(classes = {AnnotationBeanConfig.class})
public class AnnotationBeanDemo {

    @Autowired
    private GenericApplicationContext ctx;

    /**
     * 直接填class数组方式
     */
    @Test
    public void demoArrayModeImport() {

    }

    /**
     * 使用 @bean 注解表明这个方法返回的对象会以 bean 的形式添加到 spring 应
     * 用的上下文，默认情况下 bean id 使用方法名。
     */
    @Test
    public void demoGetBeanAnnotationObject() {
        Object obj = ctx.getBean("teacherBeanConfig");
        System.out.println(obj);
    }

    /**
     * ImportBeanDefinitionRegistrarf 方式
     */
    public void demoImportBeanDefinitionRegistrar() {

    }
}
