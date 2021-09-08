package com.drinks.milk.enums;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.drinks.milk.enums.base.BaseErrorEnum;

public enum ErrorEnum implements BaseErrorEnum {
    SUCCESS(10000, "成功"),
    FAILURE(99999, "失败"),
    PARAM_ERROR(19998, "参数错误"),
    EXCEPTION(19997, "异常"),
    SESSION_TIMEOUT(19995, "会话超时"),
    MOBILE_ERROR(19994, "手机号错误"),
    AUTH_ERROR(30003, "验证码错误"),
    USER_NOT_LOGIN(50001, "用户未登录"),
    USER_NOT_MATCH(50002, "权限验证信息不匹配"),
    PWD_NOT_MATCH(50003, "密码错误"),
    PARAMETER_MISSING(40001, "缺少参数异常"),
    TIME_OUT(40002, "超时异常"),
    NO_PERMISSION(60001, "没有访问权限"),
    TOKEN_EXPIRED(19999, "缓存过期"),
    PARAM_ERROR_ILLEGAL_QUERY(50102, "查询失败，请核对信息后稍后重试");

    private Integer errorCode;
    private String errorMsg;

    private ErrorEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMSG() {
        return this.errorMsg;
    }

    public void setErrorMSG(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getKey() {
        return this.errorCode;
    }

    public String getValue() {
        return this.errorMsg;
    }
}

