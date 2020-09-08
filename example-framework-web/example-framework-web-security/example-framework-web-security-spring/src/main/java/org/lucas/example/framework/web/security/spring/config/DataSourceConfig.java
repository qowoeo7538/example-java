package org.lucas.example.framework.web.security.spring.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        String userName = "develop";
        String password = "U2@amhtEs6YhU6";
        String url = "jdbc:mysql://rm-wz96378h5d05nb6f6.mysql.rds.aliyuncs.com:3306/discover_page?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=GMT%2b8&rewriteBatchedStatements=TRUE&zeroDateTimeBehavior=convertToNull";
        String driverClassName = "com.mysql.cj.jdbc.Driver";

        HikariConfig config = new HikariConfig();
        config.setUsername(userName);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setDriverClassName(driverClassName);
        return new HikariDataSource(config);
    }

}
