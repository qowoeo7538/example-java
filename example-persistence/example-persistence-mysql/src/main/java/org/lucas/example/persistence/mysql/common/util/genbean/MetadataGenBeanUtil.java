package org.lucas.example.persistence.mysql.common.util.genbean;

import org.lucas.example.persistence.mysql.common.entity.datameta.Column;
import org.lucas.example.persistence.mysql.common.util.DataSourceUtil;
import org.lucas.example.persistence.mysql.common.util.DatabaseMetaDataUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MetadataGenBeanUtil {

    public static void main(String[] args) {

    }

    public static void genJavaBeanFile(String database, String table, String beanName) throws IOException, SQLException {
        List<Column> columns = DatabaseMetaDataUtil.getColumnInfo(database, table, DataSourceUtil.getHikariDataSource());

        final BeanBuild beanBuild = new BeanBuild();
        beanBuild.setClassName(beanName);

        beanBuild.setFields(fields(columns));
    }

    public static List<String> fields(List<Column> columns) {
        return columns.stream().map(Column::getColumnName).collect(Collectors.toList());
    }

}
