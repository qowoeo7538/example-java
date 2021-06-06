package org.lucas.example.framework.spring.demo.beans.processor;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.spring.demo.beans.processor.support.MyBeanFactoryPostProcessor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {MyBeanFactoryPostProcessor.class})
public class BeanProcessorDemo {

    /**
     * beanFactory后置处理器，在beanFactory标准初始化之后调用，此时所有的bean定义已经保存加载到beanFactory中，
     * 但bean的实例还未创建，此时可以对bean的属性进行修改拓展
     */
    @Test
    public void demoBeanFactoryPostProcessor() {

    }

    /**
     * BeanDefinitionRegistryPostProcessor就是 BeanDefinition(每一个bean都会有一个对应的 BeanDefinition 实例，该实例负责
     * 保存bean对象的所有必要信息，包括bean对象的class类型、是否是抽象类、构造方法和参数、其它属性等等。) 注册中心的后置处理器， 允许
     * 我们修改拓展 BeanDefinition义信息的注册中心，在所有bean定义信息将要被加载，bean实例还未创建的时候执行
     */
    @Test
    public void demoBeanDefinitionRegistryPostProcessor() {

    }

}
