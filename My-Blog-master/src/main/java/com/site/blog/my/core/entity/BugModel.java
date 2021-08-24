package com.site.blog.my.core.entity;

import java.io.Serializable;

public class BugModel implements Serializable {
    private static final long serialVersionUID = -89958293765204L;

    /**
     *id唯一标识
     **/
    private String id;
    /**
     *id号
     **/
    private String bugNo;
    /**
     *bugName
     **/
    private String bugName;
    /**
     *bug描述
     **/
    private String bugInfo;
    /**
     *bug级别
     **/
    private String bugLevel;
    /**
     *bug出处
     **/
    private String bugLocation;
    /**
     *bug提交人
     **/
    private String createUser;
    /**
     *bug提交人
     **/
    private String updateUser;
    /**
     *bug状态
     **/
    private String bugSts;
    /**
     *bug提交时间
     **/
    private String createTime;
    /**
     *bug更新时间
     **/
    private String updateTime;
    /**
     *bug删除
     **/
    private String isDelete;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBugNo() {
        return bugNo;
    }

    public void setBugNo(String bugNo) {
        this.bugNo = bugNo;
    }

    public String getBugName() {
        return bugName;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public String getBugInfo() {
        return bugInfo;
    }

    public void setBugInfo(String bugInfo) {
        this.bugInfo = bugInfo;
    }

    public String getBugLevel() {
        return bugLevel;
    }

    public void setBugLevel(String bugLevel) {
        this.bugLevel = bugLevel;
    }

    public String getBugLocation() {
        return bugLocation;
    }

    public void setBugLocation(String bugLocation) {
        this.bugLocation = bugLocation;
    }

    public String getCreateUser() {
        return createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getBugSts() {
        return bugSts;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public void setBugSts(String bugSts) {
        this.bugSts = bugSts;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BugModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", bugNo='").append(bugNo).append('\'');
        sb.append(", bugName='").append(bugName).append('\'');
        sb.append(", bugInfo='").append(bugInfo).append('\'');
        sb.append(", bugLevel='").append(bugLevel).append('\'');
        sb.append(", bugLocation='").append(bugLocation).append('\'');
        sb.append(", createUser='").append(createUser).append('\'');
        sb.append(", updateUser='").append(updateUser).append('\'');
        sb.append(", bugSts='").append(bugSts).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append(", isDelete='").append(isDelete).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
