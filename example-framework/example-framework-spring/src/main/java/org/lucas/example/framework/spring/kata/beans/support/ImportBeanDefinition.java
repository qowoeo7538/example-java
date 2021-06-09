package org.lucas.example.framework.spring.kata.beans.support;

import org.lucas.example.common.annotation.Table;
import org.lucas.example.framework.spring.demo.beans.inject.support.Packages;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

import static org.springframework.context.annotation.AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR;

/**
 * ClassPathBeanDefinitionScanner:
 * SingletonBeanRegistry:
 * RuntimeBeanReference:
 */
public class ImportBeanDefinition implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        @NonNull BeanDefinitionRegistry registry) {
        // 获取元对象注解信息
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(Packages.class.getName());
        var basePackages = (String[]) annotationAttributes.get("value");

        var scanner = new TableClassPathScanner(registry, false);
        TypeFilter typeFilter = new AssignableTypeFilter(Comparable.class);
        TypeFilter annotationFilter = new AnnotationTypeFilter(Table.class);

        BeanNameGenerator beanNameGenerator = null;
        if (registry instanceof SingletonBeanRegistry) {
            var singletonBeanRegistry = (SingletonBeanRegistry) registry;
            beanNameGenerator = (BeanNameGenerator) singletonBeanRegistry.getSingleton(CONFIGURATION_BEAN_NAME_GENERATOR);
        }
        if (beanNameGenerator == null) {
            beanNameGenerator = new AnnotationBeanNameGenerator();
        }
        // beanName 生成
        scanner.setBeanNameGenerator(beanNameGenerator);
        // 添加扫描注解
        scanner.addIncludeFilter(annotationFilter);
        scanner.setAnnotationClass(Table.class);
        // 添加扫描类
        scanner.addIncludeFilter(typeFilter);
        // 扫描指定包并注册 beanDefinition,默认以类名小写为 beanName
        // int addCount = scanner.scan(basePackages);
        // System.out.println("扫描到类型个数:" + addCount);
        Map<String, List<RuntimeBeanReference>> pathBeanMap = scanner.doScanAndGet(basePackages);
        for (Map.Entry<String, List<RuntimeBeanReference>> entry : pathBeanMap.entrySet()) {
            for (RuntimeBeanReference reference : entry.getValue()) {
                System.out.println("beanName:" + entry.getKey() + "=" + reference);
            }
        }
    }


}
