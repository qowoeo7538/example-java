package org.lucas.example.framework.spring.demo.beans.inject.support;

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
