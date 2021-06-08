package org.lucas.example.framework.spring.demo.beans.inject;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.spring.demo.beans.inject.support.AnnotationImportDefinitionConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {AnnotationImportDefinitionConfig.class})
public class AnnotationImportDefinitionDemo {

    @Test
    public void demoImportBeanDefinitionRegistrar() {

    }

}
