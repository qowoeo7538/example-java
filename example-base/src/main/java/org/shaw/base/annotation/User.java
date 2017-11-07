package org.shaw.base.annotation;

import java.util.Date;

/**
 * Created by joy on 17-2-11.
 */
@Table(value = "user")
public class User {

    @Column("id")
    private String id;

    @Column("gmt_create")
    private Date createTime;

    @Column("gmt_modified")
    private Date updateTime;

    @Column("user_name")
    private String userName;

    @Column("user_email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
