package org.lucas.example.framework.spring.inject.impl;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({ThreadPoolTaskExecutorImport.class})
@Configuration
public class TestImport {
}
