package org.lucas.example.framework.spring.kata.beans;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.spring.kata.beans.support.AnnotationImportDefinitionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {AnnotationImportDefinitionConfig.class})
public class AnnotationImportDefinitionKata {

    @Autowired
    private GenericApplicationContext ctx;

    @Test
    public void kataImportBeanDefinitionRegistrar() {
        System.out.println("======================================");
        String[] names = ctx.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("======================================");
    }

}
