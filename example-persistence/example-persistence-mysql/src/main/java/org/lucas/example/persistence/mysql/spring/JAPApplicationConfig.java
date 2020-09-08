package org.lucas.example.persistence.mysql.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages={"org.lucas.example.persistence.mysql.spring.dao"})
@EntityScan("org.lucas.example.persistence.mysql.common.entity")
public class JAPApplicationConfig extends ApplicationConfig {
}
