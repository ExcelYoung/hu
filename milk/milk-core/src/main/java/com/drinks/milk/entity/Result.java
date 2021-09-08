package com.drinks.milk.entity;

import com.drinks.milk.enums.ErrorEnum;
import com.drinks.milk.enums.base.BaseErrorEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: 返回类
 * @date: 2020/6/9 10:21 上午
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 8240260668336306996L;

    public static final int SUCCESS_CODE = 10000;

    private Integer code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> newSuccess() {
        return new Result(10000, "Success", (Object) null);
    }

    public static <T> Result<T> newSuccess(T data) {
        return new Result(10000, "Success", data);
    }

    public static <T> Result<T> newFailure(int errorCode, String errorMsg) {
        return new Result(errorCode, errorMsg, (Object) null);
    }

    public static <T> Result<T> newFailure(BaseErrorEnum error) {
        return new Result(error.getKey(), error.getValue(), (Object) null);
    }

    public static <T> Result<T> newResult(BaseErrorEnum errorEnum) {
        return new Result(errorEnum.getKey(), errorEnum.getValue(), (Object) null);
    }

    public static <T> Result<T> newResult(BaseErrorEnum errorEnum, T data) {
        return new Result(errorEnum.getKey(), errorEnum.getValue(), data);
    }

    public static <T> Result<T> newFailure(String errorMsg) {
        return new Result(ErrorEnum.FAILURE.getErrorCode(), errorMsg, (Object) null);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
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

    public boolean isSuccess() {
        return this.code == 10000;
    }

    @Override
    public String toString() {
        return "Result [code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + "]";
    }
}
