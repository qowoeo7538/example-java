package org.lucas.example.persistence.mysql.common.entity.datameta;

public class Column {

    public String tableCat;

    public String tableSchem;

    public String tableName;

    public String columnName;

    public String dataType;

    public String typeName;

    public String columnSize;

    public String bufferLength;

    public String decimalDigits;

    public String numPrecRadix;

    public String nullable;

    public String remarks;

    public String columnDef;

    public String sqlDataType;

    public String sqlDatetimeSub;

    public String charOctetLength;

    public String ordinalPosition;

    public String isNullable;

    public String scopeCatalog;

    public String scopeSchema;

    public String scopeTable;

    public String sourceDataType;

    public String isAutoincrement;

    public String isGeneratedcolumn;

    public static class Property {

        public static final String TABLE_CAT = "TABLE_CAT";

        public static final String TABLE_SCHEM = "TABLE_SCHEM";

        public static final String TABLE_NAME = "TABLE_NAME";

        public static final String COLUMN_NAME = "COLUMN_NAME";

        public static final String DATA_TYPE = "DATA_TYPE";

        public static final String TYPE_NAME = "TYPE_NAME";

        public static final String COLUMN_SIZE = "COLUMN_SIZE";

        public static final String BUFFER_LENGTH = "BUFFER_LENGTH";

        public static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";

        public static final String NUM_PREC_RADIX = "NUM_PREC_RADIX";

        public static final String NULLABLE = "NULLABLE";

        public static final String REMARKS = "REMARKS";

        public static final String COLUMN_DEF = "COLUMN_DEF";

        public static final String SQL_DATA_TYPE = "SQL_DATA_TYPE";

        public static final String SQL_DATETIME_SUB = "SQL_DATETIME_SUB";

        public static final String CHAR_OCTET_LENGTH = "CHAR_OCTET_LENGTH";

        public static final String ORDINAL_POSITION = "ORDINAL_POSITION";

        public static final String IS_NULLABLE = "IS_NULLABLE";

        public static final String SCOPE_CATALOG = "SCOPE_CATALOG";

        public static final String SCOPE_SCHEMA = "SCOPE_SCHEMA";

        public static final String SCOPE_TABLE = "SCOPE_TABLE";

        public static final String SOURCE_DATA_TYPE = "SOURCE_DATA_TYPE";

        public static final String IS_AUTOINCREMENT = "IS_AUTOINCREMENT";

        public static final String IS_GENERATEDCOLUMN = "IS_GENERATEDCOLUMN";

    }

    public String getTableCat() {
        return tableCat;
    }

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }

    public String getBufferLength() {
        return bufferLength;
    }

    public void setBufferLength(String bufferLength) {
        this.bufferLength = bufferLength;
    }

    public String getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(String decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getNumPrecRadix() {
        return numPrecRadix;
    }

    public void setNumPrecRadix(String numPrecRadix) {
        this.numPrecRadix = numPrecRadix;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef = columnDef;
    }

    public String getSqlDataType() {
        return sqlDataType;
    }

    public void setSqlDataType(String sqlDataType) {
        this.sqlDataType = sqlDataType;
    }

    public String getSqlDatetimeSub() {
        return sqlDatetimeSub;
    }

    public void setSqlDatetimeSub(String sqlDatetimeSub) {
        this.sqlDatetimeSub = sqlDatetimeSub;
    }

    public String getCharOctetLength() {
        return charOctetLength;
    }

    public void setCharOctetLength(String charOctetLength) {
        this.charOctetLength = charOctetLength;
    }

    public String getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(String ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getScopeCatalog() {
        return scopeCatalog;
    }

    public void setScopeCatalog(String scopeCatalog) {
        this.scopeCatalog = scopeCatalog;
    }

    public String getScopeSchema() {
        return scopeSchema;
    }

    public void setScopeSchema(String scopeSchema) {
        this.scopeSchema = scopeSchema;
    }

    public String getScopeTable() {
        return scopeTable;
    }

    public void setScopeTable(String scopeTable) {
        this.scopeTable = scopeTable;
    }

    public String getSourceDataType() {
        return sourceDataType;
    }

    public void setSourceDataType(String sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public String getIsAutoincrement() {
        return isAutoincrement;
    }

    public void setIsAutoincrement(String isAutoincrement) {
        this.isAutoincrement = isAutoincrement;
    }

    public String getIsGeneratedcolumn() {
        return isGeneratedcolumn;
    }

    public void setIsGeneratedcolumn(String isGeneratedcolumn) {
        this.isGeneratedcolumn = isGeneratedcolumn;
    }

    @Override
    public String toString() {
        return "Column{" +
                "tableCat='" + tableCat + '\'' +
                ", tableSchem='" + tableSchem + '\'' +
                ", tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", typeName='" + typeName + '\'' +
                ", columnSize='" + columnSize + '\'' +
                ", bufferLength='" + bufferLength + '\'' +
                ", decimalDigits='" + decimalDigits + '\'' +
                ", numPrecRadix='" + numPrecRadix + '\'' +
                ", nullable='" + nullable + '\'' +
                ", remarks='" + remarks + '\'' +
                ", columnDef='" + columnDef + '\'' +
                ", sqlDataType='" + sqlDataType + '\'' +
                ", sqlDatetimeSub='" + sqlDatetimeSub + '\'' +
                ", charOctetLength='" + charOctetLength + '\'' +
                ", ordinalPosition='" + ordinalPosition + '\'' +
                ", isNullable='" + isNullable + '\'' +
                ", scopeCatalog='" + scopeCatalog + '\'' +
                ", scopeSchema='" + scopeSchema + '\'' +
                ", scopeTable='" + scopeTable + '\'' +
                ", sourceDataType='" + sourceDataType + '\'' +
                ", isAutoincrement='" + isAutoincrement + '\'' +
                ", isGeneratedcolumn='" + isGeneratedcolumn + '\'' +
                '}';
    }
}
