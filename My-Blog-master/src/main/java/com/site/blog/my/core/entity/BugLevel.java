package com.site.blog.my.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BugLevel {

    private Integer levelId;
    private String levelName;
    private String levelSts;
    private String levelIcon;
    private Byte   isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date levelCreateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date levelUpdateTime;

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setLevelCreateTime(Date levelCreateTime) {
        this.levelCreateTime = levelCreateTime;
    }

    public void setLevelUpdateTime(Date levelUpdateTime) {
        this.levelUpdateTime = levelUpdateTime;
    }

    public String getLevelIcon() {
        return levelIcon;
    }

    public void setLevelIcon(String levelIcon) {
        this.levelIcon = levelIcon;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelSts() {
        return levelSts;
    }

    public void setLevelSts(String levelSts) {
        this.levelSts = levelSts;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BugLevel{");
        sb.append("levelId=").append(levelId);
        sb.append(", levelName='").append(levelName).append('\'');
        sb.append(", levelSts='").append(levelSts).append('\'');
        sb.append(", levelIcon='").append(levelIcon).append('\'');
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", levelCreateTime=").append(levelCreateTime);
        sb.append(", levelUpdateTime=").append(levelUpdateTime);
        sb.append('}');
        return sb.toString();
    }
}
