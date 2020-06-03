package org.lucas.example.framework.spring.demo.inject;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.spring.common.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 注解 @Import 可以用于导入第三方包,把实例加入 spring 的 IOC 容器中
 */
@SpringJUnitConfig(locations = {"classpath:inject/applicationContext-base.xml"})
public class ImportDemo {

    @Autowired
    private GenericApplicationContext ctx;

    /**
     * 直接填class数组方式
     */
    @Test
    public void demoArrayModeImport() {

    }

    /**
     * ImportSelector 方式
     */
    @Test
    public void demoImportSelector() {
        Student student = ctx.getBean(Student.class);
        System.out.println(student);
    }

    /**
     * ImportBeanDefinitionRegistrarf 方式
     */
    public void demoImportBeanDefinitionRegistrar() {

    }
}
