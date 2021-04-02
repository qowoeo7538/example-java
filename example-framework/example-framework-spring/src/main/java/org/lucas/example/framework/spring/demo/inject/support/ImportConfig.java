package org.lucas.example.framework.spring.demo.inject.support;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({BeanImportSelector.class})
@Configuration
public class ImportConfig {
}
