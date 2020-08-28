package org.lucas.example.persistence.mysql.common.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public abstract class DataSourceUtil {

    private static final String USER_NAME = "spring.datasource.username";

    private static final String PASSWORD = "spring.datasource.password";

    private static final String URL = "spring.datasource.url";

    private static final String DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";

    public static DataSource getHikariDataSource() throws IOException {
        Properties properties = PropertiesUtil.getConfig();
        String userName = properties.getProperty(USER_NAME);
        String password = properties.getProperty(PASSWORD);
        String url = properties.getProperty(URL);
        String driverClassName = properties.getProperty(DRIVER_CLASS_NAME);

        HikariConfig config = new HikariConfig();
        config.setUsername(userName);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setDriverClassName(driverClassName);
        return new HikariDataSource(config);
    }

}
