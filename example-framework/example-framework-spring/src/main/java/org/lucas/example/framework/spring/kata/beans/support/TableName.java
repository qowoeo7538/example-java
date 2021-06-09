package org.lucas.example.framework.spring.kata.beans.support;

import java.util.Objects;

public class TableName {

    private String beanName;

    private String name;

    public TableName(String beanName, String name) {
        this.beanName = beanName;
        this.name = name;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableName tableName = (TableName) o;
        return Objects.equals(beanName, tableName.beanName) && Objects.equals(name, tableName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName, name);
    }
}
