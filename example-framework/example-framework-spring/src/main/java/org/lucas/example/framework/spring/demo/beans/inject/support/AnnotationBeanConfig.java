package org.lucas.example.framework.spring.demo.beans.inject.support;

import org.lucas.example.common.pojo.entity.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationBeanConfig {

    @Bean("teacherBeanConfig")
    public Teacher getTeacher() {
        return new Teacher();
    }

}
