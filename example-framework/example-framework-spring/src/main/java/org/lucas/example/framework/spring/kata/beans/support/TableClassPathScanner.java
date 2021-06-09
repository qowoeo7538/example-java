package org.lucas.example.framework.spring.kata.beans.support;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TableClassPathScanner extends ClassPathBeanDefinitionScanner {

    private final Set<TableName> existBeanNames = new HashSet<>();

    private Class<? extends Annotation> annotationClass;

    public TableClassPathScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    public Map<String, List<RuntimeBeanReference>> doScanAndGet(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        Map<String, List<RuntimeBeanReference>> returnGroupMap = new HashMap<>(16);
        if (!beanDefinitions.isEmpty() && !existBeanNames.isEmpty()) {
            Map<String, Set<RuntimeBeanReference>> groupBeanMap = new HashMap<>();
            processBeanDefinitions(groupBeanMap, beanDefinitions);
            processBeanDefinitionsWithExistBeanNames(groupBeanMap, existBeanNames);
            for (Map.Entry<String, Set<RuntimeBeanReference>> entry : groupBeanMap.entrySet()) {
                String group = entry.getKey();
                List<RuntimeBeanReference> referenceList = returnGroupMap.get(group);
                if (referenceList == null) {
                    referenceList = new ManagedList<>();
                    returnGroupMap.put(group, referenceList);
                }
                referenceList.addAll(entry.getValue());
            }
        }
        return returnGroupMap;
    }

    /**
     * super.doScan(basePackages)方法会调用 checkCandidate(String beanName, BeanDefinition beanDefinition) 方法
     */
    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            ScannedGenericBeanDefinition definition = (ScannedGenericBeanDefinition) beanDefinition;
            AnnotationAttributes annotationAttrs = AnnotationAttributes.fromMap(definition.getMetadata().getAnnotationAttributes(annotationClass.getName()));
            var value = annotationAttrs.getString("value");
            existBeanNames.add(new TableName(beanName, value));
            return false;
        }
    }

    private void processBeanDefinitions(Map<String, Set<RuntimeBeanReference>> tableMap, Set<BeanDefinitionHolder> beanDefinitions) {
        ScannedGenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (ScannedGenericBeanDefinition) holder.getBeanDefinition();
            var annotationAttrs = AnnotationAttributes.fromMap(definition.getMetadata().getAnnotationAttributes(this.annotationClass.getName()));
            var value = annotationAttrs.getString("value");
            if (StringUtils.isBlank(value)) {
                throw new IllegalArgumentException();
            }
            Set<RuntimeBeanReference> listenerSet = tableMap.computeIfAbsent(value, k -> new HashSet<>());
            listenerSet.add(new RuntimeBeanReference(holder.getBeanName()));
        }
    }

    private void processBeanDefinitionsWithExistBeanNames(Map<String, Set<RuntimeBeanReference>> groupBeanMap, Set<TableName> existBeanNames) {
        for (TableName groupBeanName : existBeanNames) {
            String group = groupBeanName.getName();
            if (StringUtils.isBlank(group)) {
                throw new IllegalArgumentException("BeanName[" + groupBeanName.getBeanName() + "] " + this.annotationClass.getSimpleName() + " group must not empty!");
            }
            Set<RuntimeBeanReference> listenerSet = groupBeanMap.get(group);

            if (listenerSet == null) {
                listenerSet = new HashSet<>();
                groupBeanMap.put(group, listenerSet);
            }
            listenerSet.add(new RuntimeBeanReference(groupBeanName.getBeanName()));

        }
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

}
