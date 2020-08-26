package org.lucas.example.persistence.mysql.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * 注解 @ImportResource：自定义的 xml 配置文件
 * 注解 @PropertySource：用于引入 *.Properties或者 .yml 配置文件
 */
@PropertySource(value = {"classpath:datasource.properties"})
@SpringBootApplication(scanBasePackages = "org.lucas.example.persistence.mysql.spring")
public class ApplicationConfig {

}
