package org.lucas.example.persistence.mysql.common.genbean;

public class BeanField {

    private String type;

    private String fieldName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String toCode() {
        return "private " + this.type + " " + this.fieldName + ";";
    }
}
