package org.lucas.example.framework.spring.demo.beans.processor.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        int count = beanFactory.getBeanDefinitionCount();
        String[] names = beanFactory.getBeanDefinitionNames();
        System.out.println("===================== BeanFactoryPostProcessor =====================");
        for (String beanName : names) {
            System.out.println(beanName);
        }
        System.out.println("当前BeanFactory中有" + count + "个Bean");
        System.out.println("===================== BeanFactoryPostProcessor =====================");
    }

}
