package org.lucas.demo.info.clazz.impl;

import java.util.Date;

@InheritedAnnotation("base")
public class BaseEntity {

    @Column("gmt_create")
    private Date createTime;

    @Column("gmt_modified")
    private Date updateTime;

    public BaseEntity() {
    }

    public BaseEntity(String id, Date createTime, Date updateTime) {
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
