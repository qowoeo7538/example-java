package org.lucas.example.persistence.mysql.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 注解 @ManyToMany(targetEntity = class)：表明多对多关系
 * <p>
 * 注解 @Table：如果没有该注解，默认通过类名转换
 */
@Entity
@Table(name = "discover_live_permission")
public class AutoIdCommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "publish_name")
    private String column0;

    @Column(name = "publish_type")
    private String column1;

    @Column(name = "create_time")
    private LocalDateTime column2;

    @Column(name = "create_user_id")
    private String column3;

    @Column(name = "update_user_id")
    private String column4;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumn0() {
        return column0;
    }

    public void setColumn0(String column0) {
        this.column0 = column0;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public LocalDateTime getColumn2() {
        return column2;
    }

    public void setColumn2(LocalDateTime column2) {
        this.column2 = column2;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public String getColumn4() {
        return column4;
    }

    public void setColumn4(String column4) {
        this.column4 = column4;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @PrePersist
    public void updateTime() {
        this.updateTime = LocalDateTime.now();
    }

}
