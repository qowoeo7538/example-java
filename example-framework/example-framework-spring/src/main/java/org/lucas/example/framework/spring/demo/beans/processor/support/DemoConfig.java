package org.lucas.example.framework.spring.demo.beans.processor.support;

import org.lucas.example.common.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DemoConfig {

    @Bean
    public User user(){
        return new User();
    }

}
