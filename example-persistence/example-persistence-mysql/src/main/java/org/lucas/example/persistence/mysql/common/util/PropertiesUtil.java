package org.lucas.example.persistence.mysql.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class PropertiesUtil {

    public static Properties getConfig() throws IOException {
        Properties properties = new Properties();
        InputStream stream = PropertiesUtil.class.getResourceAsStream("/datasource.properties");
        properties.load(stream);
        return properties;
    }

}
