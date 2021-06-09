package org.lucas.example.framework.spring.kata.beans.support;

import org.lucas.example.framework.spring.demo.beans.inject.support.Packages;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({ImportBeanDefinition.class})
@Configuration
@Packages({
        "org.lucas.example.framework.spring.demo.beans.inject.support",
        "org.lucas.example.common.entity"
})
public class AnnotationImportDefinitionConfig {
}
