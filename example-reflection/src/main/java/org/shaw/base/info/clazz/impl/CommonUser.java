package org.shaw.base.info.clazz.impl;

import java.util.Date;

@InheritedAnnotation("parent")
@Table(value = "common_user")
public class CommonUser {

    @Column("id")
    private String id;

    @Column("gmt_create")
    private Date createTime;

    @Column("gmt_modified")
    private Date updateTime;

    public CommonUser() {
    }

    public CommonUser(String id, Date createTime, Date updateTime) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
