package org.lucas.example.framework.spring.demo.beans.inject;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.entity.Student;
import org.lucas.example.common.entity.User;
import org.lucas.example.framework.spring.demo.beans.inject.support.AnnotationImportConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {AnnotationImportConfig.class})
public class AnnotationImportDemo {

    @Autowired
    private GenericApplicationContext ctx;

    /**
     * ImportSelector 方式和 ImportBeanDefinitionRegistrar 方式
     */
    @Test
    public void demoAnnotationImportSelector() {
        var student = ctx.getBean(Student.class);
        System.out.println(student);
    }

    /**
     * ImportBeanDefinitionRegistrar 方式
     */
    @Test
    public void demoAnnotationImportRegistrar() {
        var user = ctx.getBean(User.class);
        System.out.println(user);
    }

}
