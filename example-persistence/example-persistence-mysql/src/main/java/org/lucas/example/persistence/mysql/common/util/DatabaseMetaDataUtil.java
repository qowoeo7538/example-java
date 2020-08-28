package org.lucas.example.persistence.mysql.common.util;

import org.eclipse.collections.api.factory.Lists;
import org.lucas.example.persistence.mysql.common.entity.Table;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public abstract class DatabaseMetaDataUtil {

    /**
     * 单字符通配符"_"
     */
    public static String SINGLE_WILDCARD = "_";

    /**
     * 多字符通配符"_"
     */
    public static String MULTIPLE_WILDCARD = "%";

    public static void main(String[] args) throws SQLException, IOException {
        List<Table> tables = getTableInfo("mall_message", DataSourceUtil.getHikariDataSource());
        for (Table table : tables) {
            System.out.println(table);
        }
    }

    public static List<String> getColumnInfo(List<String> databases, DataSource dataSource) throws SQLException {
        List<String> result = new ArrayList<>();
        ResultSet databaseResult = null;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            databaseMetaData.getColumns()
        }
    }

    /**
     * 获取所有库名
     */
    public static List<String> getDatabaseInfo(DataSource dataSource) throws SQLException {
        List<String> result = new ArrayList<>();
        ResultSet databaseResult = null;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            databaseResult = databaseMetaData.getCatalogs();
            while (databaseResult.next()) {
                result.add(databaseResult.getString("TABLE_CAT"));
            }
        } finally {
            if (Objects.nonNull(databaseResult)) {
                databaseResult.close();
            }
        }
        return result;
    }

    public static List<Table> getTableInfo(String database, DataSource dataSource) throws SQLException {
        return getTableInfo(Lists.mutable.of(database), null, dataSource);
    }

    public static List<Table> getTableInfo(List<String> databases, List<String> types, DataSource dataSource) throws SQLException {
        List<Table> result = new ArrayList<>();
        for (String database : databases) {
            ResultSet tableResult = null;
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                String[] typeArray;
                if (CollectionUtils.isEmpty(types)) {
                    typeArray = new String[]{Table.Type.TABLE};
                } else {
                    typeArray = types.toArray(new String[0]);
                }
                tableResult = databaseMetaData.getTables(database, MULTIPLE_WILDCARD, MULTIPLE_WILDCARD, typeArray);
                while (tableResult.next()) {
                    Table table = new Table();
                    table.setRefGeneration(tableResult.getString(Table.Property.REF_GENERATION));
                    table.setDatabaseName(tableResult.getString(Table.Property.TABLE_CAT));
                    table.setDatabaseType(tableResult.getString(Table.Property.TYPE_CAT));
                    table.setRemarks(tableResult.getString(Table.Property.REMARKS));
                    table.setSelfReferencingColName(tableResult.getString(Table.Property.SELF_REFERENCING_COL_NAME));
                    table.setTableSchema(tableResult.getString(Table.Property.TABLE_SCHEM));
                    table.setTableName(tableResult.getString(Table.Property.TABLE_NAME));
                    table.setTypeName(tableResult.getString(Table.Property.TYPE_NAME));
                    table.setTypeSchema(tableResult.getString(Table.Property.TYPE_SCHEM));
                    table.setTableType(tableResult.getString(Table.Property.TABLE_TYPE));
                    result.add(table);
                }
            } finally {
                if (Objects.nonNull(tableResult)) {
                    tableResult.close();
                }
            }
        }
        return result;
    }

    public static final String TABLE_NAME = "t_application";

    public static void generateBean() throws IOException, SQLException {
        DataSource dataSource = DataSourceUtil.getHikariDataSource();
        ResultSet tableResult = null;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            tableResult = databaseMetaData.getTables(null, MULTIPLE_WILDCARD, TABLE_NAME, new String[]{"TABLE"});
            while (tableResult.next()) {
                ResultSet columnsResult = databaseMetaData.getColumns(null, MULTIPLE_WILDCARD, TABLE_NAME, MULTIPLE_WILDCARD);
                while (columnsResult.next()) {
                    String columnName = columnsResult.getString("COLUMN_NAME");
                    String columnType = columnsResult.getString("TYPE_NAME").toLowerCase();
                    String remarks = columnsResult.getString("REMARKS");
                    String fieldTye;
                    switch (columnType) {
                        case "varchar":
                            fieldTye = "String";
                            break;
                        case "int":
                            fieldTye = "int";
                            break;
                        default:
                            fieldTye = "String";
                    }
                    System.out.println("columnName:" + columnName);
                    System.out.println("columnType:" + columnType);
                    System.out.println("remarks:" + remarks);
                    System.out.println("fieldTye:" + fieldTye);
                }
            }
        } finally {
            if (Objects.nonNull(tableResult)) {
                tableResult.close();
            }
        }
    }


}
