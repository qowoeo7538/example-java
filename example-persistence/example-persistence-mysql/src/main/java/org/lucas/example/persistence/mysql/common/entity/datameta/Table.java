package org.lucas.example.persistence.mysql.common.entity.datameta;

public class Table {

    /**
     * 数据库名称：TABLE_CAT
     */
    private String databaseName;

    /**
     * 数据库类型：TYPE_CAT
     */
    private String databaseType;

    /**
     * 模式类型：TYPE_SCHEM
     */
    private String typeSchema;

    /**
     * 类型名称：TYPE_NAME
     */
    private String typeName;

    /**
     * 表名：TABLE_NAME
     */
    private String tableName;

    /**
     * 表模式：TABLE_SCHEM
     */
    private String tableSchema;

    /**
     * 表类型：TABLE_TYPE
     */
    private String tableType;

    /**
     * 表注解：REMARKS
     */
    private String remarks;

    private String selfReferencingColName;

    private String refGeneration;

    public static class Property{

        public static final String TABLE_CAT = "TABLE_CAT";

        public static final String TYPE_CAT = "TYPE_CAT";

        public static final String TYPE_SCHEM = "TYPE_SCHEM";

        public static final String TYPE_NAME = "TYPE_NAME";

        public static final String TABLE_NAME = "TABLE_NAME";

        public static final String TABLE_SCHEM = "TABLE_SCHEM";

        public static final String TABLE_TYPE = "TABLE_TYPE";

        public static final String REMARKS = "REMARKS";

        public static final String SELF_REFERENCING_COL_NAME = "SELF_REFERENCING_COL_NAME";

        public static final String REF_GENERATION = "REF_GENERATION";

    }

    public static class Type{
        /**
         * 普通表
         */
        public static final String TABLE = "TABLE";

        /**
         * 视图表
         */
        public static final String VIEW_TABLE = "VIEW";

        /**
         * 系统表
         */
        public static final String SYSTEM_TABLE = "SYSTEM TABLE";

        /**
         * 临时表
         */
        public static final String GLOBAL_TEMPORARY = "GLOBAL TEMPORARY";

        /**
         * 临时表
         */
        public static final String LOCAL_TEMPORARY = "LOCAL TEMPORARY";

        /**
         * 别名表
         */
        public static final String ALIAS_TABLE = "ALIAS";

        public static final String SYNONYM = "SYNONYM";
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getTypeSchema() {
        return typeSchema;
    }

    public void setTypeSchema(String typeSchema) {
        this.typeSchema = typeSchema;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSelfReferencingColName() {
        return selfReferencingColName;
    }

    public void setSelfReferencingColName(String selfReferencingColName) {
        this.selfReferencingColName = selfReferencingColName;
    }

    public String getRefGeneration() {
        return refGeneration;
    }

    public void setRefGeneration(String refGeneration) {
        this.refGeneration = refGeneration;
    }

    @Override
    public String toString() {
        return "Table{" +
                "databaseName='" + databaseName + '\'' +
                ", databaseType='" + databaseType + '\'' +
                ", typeSchem='" + typeSchema + '\'' +
                ", typeName='" + typeName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableSchema='" + tableSchema + '\'' +
                ", tableType='" + tableType + '\'' +
                ", remarks='" + remarks + '\'' +
                ", selfReferencingColName='" + selfReferencingColName + '\'' +
                ", refGeneration='" + refGeneration + '\'' +
                '}';
    }

}
