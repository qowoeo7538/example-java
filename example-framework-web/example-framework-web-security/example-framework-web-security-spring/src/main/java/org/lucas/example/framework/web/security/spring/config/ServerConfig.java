package org.lucas.example.framework.web.security.spring.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


/**
 * SpringBootApplication 三个注解的组合:
 * 1.@Configuration 标明该类使用Spring基于Java的配置类.
 * 2.@ComponentScan 组件扫描,扫描组件并注册为Spring应用程序上下文里的Bean。
 * 3.@EnableAutoConfiguration 开启了Spring Boot自动配置.
 */
@SpringBootApplication(scanBasePackages = "org.lucas.example.framework.web.security.spring")
@EnableConfigurationProperties(AppConfig.class)
public class ServerConfig {


}
