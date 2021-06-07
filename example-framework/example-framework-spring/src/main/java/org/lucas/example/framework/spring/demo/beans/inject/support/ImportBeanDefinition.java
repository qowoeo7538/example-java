package org.lucas.example.framework.spring.demo.beans.inject.support;

import org.lucas.example.common.entity.User;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Map;


public class ImportBeanDefinition implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {

        //扫描注解
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(Configuration.class.getName());
        String[] basePackages = (String[]) annotationAttributes.get("*");

        //扫描类
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false);
        TypeFilter helloServiceFilter = new AssignableTypeFilter(User.class);

        scanner.addIncludeFilter(helloServiceFilter);
        scanner.scan(basePackages);
    }

}
