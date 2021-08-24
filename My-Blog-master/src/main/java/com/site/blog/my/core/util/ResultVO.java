package com.site.blog.my.core.util;

import java.io.Serializable;

public class ResultVO<T> implements Serializable {
    private Boolean success;
    private Integer code;
    private String msg;
    private T data;

    public ResultVO() {
    }

    public static <T> ResultVO<T> success() {
        return success((String)null);
    }

    public static <T> ResultVO<T> success(String msg) {
        return (ResultVO<T>) success((Object)null, msg);
    }

    public static <T> ResultVO<T> success(T data, String msg) {
        ResultVO<T> result = new ResultVO();
        result.setSuccess(true);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> ResultVO<T> fail() {
        return fail((String)null);
    }

    public static <T> ResultVO<T> fail(String msg) {
        return (ResultVO<T>) fail((Object)null, msg);
    }

    public static <T> ResultVO<T> fail(T data, String msg) {
        ResultVO<T> result = new ResultVO();
        result.setSuccess(false);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Result{");
        sb.append("success=").append(this.success);
        sb.append(", code=").append(this.code);
        sb.append(", msg='").append(this.msg).append('\'');
        sb.append(", data=").append(this.data);
        sb.append('}');
        return sb.toString();
    }
}

