package org.lucas.example.persistence.mysql.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * 注解 @ManyToMany(targetEntity = class) 表明多对多关系
 */
@Entity(name = "discover_live_permission")
public class AutoIdCommonEntiyt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "publish_name")
    private String column0;


    @Column(name = "publish_type")
    private String column1;

    @Column(name = "create_time")
    private String column2;

    @Column(name = "update_time")
    private String column3;

    @Column(name = "create_user_id")
    private String column4;

    @Column(name = "update_user_id")
    private String column5;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColumn0() {
        return column0;
    }

    public void setColumn0(String column0) {
        this.column0 = column0;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
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

    public String getColumn5() {
        return column5;
    }

    public void setColumn5(String column5) {
        this.column5 = column5;
    }

    private String status;

    public static enum Status {

    }

}
