package org.lucas.example.persistence.mysql.common.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public abstract class DataSourceBeanUtil {


    public static void main(String[] args) throws SQLException, IOException {
        generateBean();
    }

    public static void generateBean() throws IOException {
        DataSource dataSource = getDataSource();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            databaseMetaData.getTables()

            statement = connection
                    .prepareStatement("SELECT * FROM t_application");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("configuration"));
            }
        } catch (final SQLException e) {

        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    private static final String USER_NAME = "spring.datasource.username";

    private static final String PASSWORD = "spring.datasource.password";

    private static final String URL = "spring.datasource.url";

    private static final String DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";

    private static DataSource getDataSource() throws IOException {
        Properties properties = getConfig();
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

    private static Properties getConfig() throws IOException {
        Properties properties = new Properties();
        InputStream stream = DataSourceBeanUtil.class.getResourceAsStream("/datasource.properties");
        properties.load(stream);
        return properties;
    }

}
