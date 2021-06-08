package org.lucas.example.framework.spring.demo.beans.inject.support;

import org.lucas.example.common.annotation.Table;
import org.lucas.example.common.entity.User;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Map;

/**
 * ClassPathBeanDefinitionScanner:
 * SingletonBeanRegistry:
 */
public class ImportBeanDefinition implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {

        // 获取元对象注解信息
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(Packages.class.getName());
        String[] basePackages = (String[]) annotationAttributes.get("value");

        var scanner = new ClassPathBeanDefinitionScanner(registry, false);
        TypeFilter typeFilter = new AssignableTypeFilter(User.class);
        TypeFilter annotationFilter = new AnnotationTypeFilter(Table.class);
        // 添加扫描注解
        scanner.addIncludeFilter(annotationFilter);
        // 添加扫描类
        scanner.addIncludeFilter(typeFilter);
        // 扫描指定包并注册 beanDefinition,默认以类名小写为 beanName
        scanner.scan(basePackages);
    }

}
