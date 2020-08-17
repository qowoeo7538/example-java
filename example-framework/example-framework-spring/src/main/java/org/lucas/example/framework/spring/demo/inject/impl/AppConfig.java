package org.lucas.example.framework.spring.demo.inject.impl;

import org.lucas.example.framework.spring.common.bean.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("teacher")
    public Teacher getTeacher() {
        return new Teacher();
    }

}
