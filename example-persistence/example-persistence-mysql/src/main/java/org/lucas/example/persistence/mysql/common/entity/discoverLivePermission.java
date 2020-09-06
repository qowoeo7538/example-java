package org.lucas.example.persistence.mysql.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class discoverLivePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String publishName;
    private String publishType;
    private String createTime;
    private String updateTime;
    private String createUserId;
    private String updateUserId;

    public static enum PublishType {
        A, B;
    }

    /**
     * Returns 发现发布权限配置主键ID
     *
     * @return 发现发布权限配置主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns 发布类型名
     *
     * @return 发布类型名
     */
    public String getPublishName() {
        return publishName;
    }

    /**
     * Returns 发布类型值：-1-普通用户 1-主播用户
     *
     * @return 发布类型值：-1-普通用户 1-主播用户
     */
    public String getPublishType() {
        return publishType;
    }

    /**
     * Returns 创建时间
     *
     * @return 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * Returns 更新时间
     *
     * @return 更新时间
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * Returns 创建人id
     *
     * @return 创建人id
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * Returns 更新人id
     *
     * @return 更新人id
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * Sets 发现发布权限配置主键ID
     *
     * @param id is 发现发布权限配置主键ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets 发布类型名
     *
     * @param publishName is 发布类型名
     */
    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

    /**
     * Sets 发布类型值：-1-普通用户 1-主播用户
     *
     * @param publishType is 发布类型值：-1-普通用户 1-主播用户
     */
    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    /**
     * Sets 创建时间
     *
     * @param createTime is 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * Sets 更新时间
     *
     * @param updateTime is 更新时间
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Sets 创建人id
     *
     * @param createUserId is 创建人id
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * Sets 更新人id
     *
     * @param updateUserId is 更新人id
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
}