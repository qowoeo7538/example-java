package org.lucas.example.persistence.mysql.common.genbean;

import org.junit.Test;
import org.lucas.example.persistence.mysql.common.entity.datameta.Column;
import org.lucas.example.persistence.mysql.common.util.DataSourceUtil;
import org.lucas.example.persistence.mysql.common.util.DatabaseMetaDataUtil;

import java.util.ArrayList;
import java.util.List;

public class BeanGeneratorTests {

    @Test
    public void testGenerateBean() throws Exception {
        List<Column> columns = DatabaseMetaDataUtil.getColumnInfo("mall_message", "t_application", DataSourceUtil.getHikariDataSource());

        List<Property> props = new ArrayList<>();
        for (Column column : columns) {
            Property property = new Property();
            property.setType("String");
            property.setName(column.getColumnName());
            property.setComment(column.getRemarks());
            props.add(property);
        }

        BeanGenerator.builder()
                .name("Application")
                .pack("org.lucas.example.persistence.mysql.common.entity")
                .properties(props)
                .write();
    }

}
