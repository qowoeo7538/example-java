package org.lucas.example.framework.spring.demo.beans.processor;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.spring.demo.beans.processor.support.MyBeanFactoryPostProcessor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {MyBeanFactoryPostProcessor.class})
public class BeanFactoryPostProcessorDemo {

    /**
     * beanFactory后置处理器，在beanFactory标准初始化之后调用，此时所有的bean定义已经保存加载到beanFactory中，
     * 但bean的实例还未创建，此时可以对bean的属性进行修改拓展
     */
    @Test
    public void demoBeanFactoryPostProcessor() {

    }

}
