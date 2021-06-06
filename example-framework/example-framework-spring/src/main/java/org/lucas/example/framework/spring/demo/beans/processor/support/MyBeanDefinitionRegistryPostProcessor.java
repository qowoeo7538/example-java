package org.lucas.example.framework.spring.demo.beans.processor.support;

import org.lucas.example.common.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 先执行postProcessBeanDefinitionRegistry方法,再执行postProcessBeanFactory方法
 * <p>
 * 执行流程：
 * 1. AnnotationConfigApplicationContext：创建IOC对象
 * 2. refresh()——>invokeBeanFactoryPostProcessors()：执行BeanDefinitionRegistryPostProcessor
 * 3. 从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件
 * 3.1 依次触发所有的postProcessBeanDefinitionRegistry()方法
 * 3.2 再来触发postProcessorBeanFactory()方法BeanFactoryPostProcessor
 * 4. 再来从容器中找到BeanFactoryPostProcessor组件，然后依次触发postProcessorBeanFactory()方法
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("===================== postProcessBeanDefinitionRegistry =====================");
        System.out.println("bean的数量：" + registry.getBeanDefinitionCount());
        String[] names = registry.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .rootBeanDefinition(User.class)
                .getBeanDefinition();
        // 注册 hello 名称的 User 对象
        registry.registerBeanDefinition("hello", beanDefinition);
        System.out.println("===================== postProcessBeanDefinitionRegistry =====================");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("===================== postProcessBeanFactory =====================");
        System.out.println("bean的数量：" + beanFactory.getBeanDefinitionCount());
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("===================== postProcessBeanFactory =====================");
    }

}
