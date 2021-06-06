package org.lucas.example.framework.spring.demo.beans.processor.support;

import org.lucas.example.common.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Bean
    public User user() {
        return new User();
    }

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
