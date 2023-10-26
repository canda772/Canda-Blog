package com.site.blog.my.core.entity;

public class AdminUser {
    /**
     * 用户userId
     */
    private Integer adminUserId;
    /**
     * 用户名称
     */
    private String loginUserName;
    /**
     * 登录密码
     */
    private String loginPassword;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户手机号
     */
    private String mobileNo;
    /**
     * 用户状态
     */
    private String locked;

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName == null ? null : loginUserName.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "adminUserId=" + adminUserId +
                ", loginUserName='" + loginUserName + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", locked='" + locked + '\'' +
                '}';
    }
}