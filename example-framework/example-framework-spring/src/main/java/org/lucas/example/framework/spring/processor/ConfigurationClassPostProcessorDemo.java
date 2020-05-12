package org.lucas.example.framework.spring.processor;

/**
 * ConfigurationClassPostProcessor 用来解析注解类，并把其注册到 Spring 容器中的，其可以
 * 解析标注 @Configuration、@Component、@ComponentScan、@Import、@ImportResource 等的 Bean。
 * 当我们使用 <context:annotation-config/> 或者 <context:component-scan/> 时，
 * Spring 容器会默认把 ConfigurationClassPostProcessor 处理器注入 Spring 容器。
 */
public class ConfigurationClassPostProcessorDemo {
}
