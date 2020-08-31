package org.lucas.example.persistence.mysql.common.util;

import org.eclipse.collections.api.factory.Lists;
import org.lucas.example.persistence.mysql.common.entity.datameta.Column;
import org.lucas.example.persistence.mysql.common.entity.datameta.Table;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
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

    /**
     * 获取表信息
     *
     * @param database   库
     * @param dataSource 数据源
     * @return 表信息
     * @throws SQLException
     */
    public static List<Table> getTableInfo(String database, DataSource dataSource) throws SQLException {
        return getTableInfo(Lists.mutable.of(database), null, dataSource);
    }

    /**
     * 获取表信息
     *
     * @param databases  库
     * @param types      表类型
     * @param dataSource 数据源
     * @return 表信息
     * @throws SQLException
     */
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

    /**
     * 获取字段信息
     *
     * @param database   库
     * @param tableName  表
     * @param dataSource 数据源
     * @return 字段信息
     * @throws SQLException
     */
    public static List<Column> getColumnInfo(String database, String tableName, DataSource dataSource) throws SQLException {
        List<Column> result = new ArrayList<>();
        ResultSet columnResult = null;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            columnResult = databaseMetaData.getColumns(database, MULTIPLE_WILDCARD, tableName, MULTIPLE_WILDCARD);
            while (columnResult.next()) {
                Column column = new Column();
                column.setTableCat(columnResult.getString(Column.Property.TABLE_CAT));
                column.setTableSchem(columnResult.getString(Column.Property.TABLE_SCHEM));
                column.setTableName(columnResult.getString(Column.Property.TABLE_NAME));
                column.setColumnName(columnResult.getString(Column.Property.COLUMN_NAME));
                column.setDataType(columnResult.getString(Column.Property.DATA_TYPE));
                column.setTypeName(columnResult.getString(Column.Property.TYPE_NAME));
                column.setColumnSize(columnResult.getString(Column.Property.COLUMN_SIZE));
                column.setBufferLength(columnResult.getString(Column.Property.BUFFER_LENGTH));
                column.setDecimalDigits(columnResult.getString(Column.Property.DECIMAL_DIGITS));
                column.setNumPrecRadix(columnResult.getString(Column.Property.NUM_PREC_RADIX));
                column.setNullable(columnResult.getString(Column.Property.NULLABLE));
                column.setRemarks(columnResult.getString(Column.Property.REMARKS));
                column.setColumnDef(columnResult.getString(Column.Property.COLUMN_DEF));
                column.setSqlDataType(columnResult.getString(Column.Property.SQL_DATA_TYPE));
                column.setSqlDatetimeSub(columnResult.getString(Column.Property.SQL_DATETIME_SUB));
                column.setCharOctetLength(columnResult.getString(Column.Property.CHAR_OCTET_LENGTH));
                column.setOrdinalPosition(columnResult.getString(Column.Property.ORDINAL_POSITION));
                column.setIsNullable(columnResult.getString(Column.Property.IS_NULLABLE));
                column.setScopeCatalog(columnResult.getString(Column.Property.SCOPE_CATALOG));
                column.setScopeSchema(columnResult.getString(Column.Property.SCOPE_SCHEMA));
                column.setScopeTable(columnResult.getString(Column.Property.SCOPE_TABLE));
                column.setSourceDataType(columnResult.getString(Column.Property.SOURCE_DATA_TYPE));
                column.setIsAutoincrement(columnResult.getString(Column.Property.IS_AUTOINCREMENT));
                column.setIsGeneratedcolumn(columnResult.getString(Column.Property.IS_GENERATEDCOLUMN));
                result.add(column);
            }
        } finally {
            if (Objects.nonNull(columnResult)) {
                columnResult.close();
            }
        }
        return result;
    }

}
