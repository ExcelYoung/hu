package com.drinks.milk.enums;

import com.drinks.milk.enums.base.BaseIntegerEnum;
public enum VerifCodeType implements BaseIntegerEnum {

    REGISTER(10, "注册账号"),
    FIND_PASSWORD(11, "找回密码"),
    MODIFY_PASSWORD(12, "修改密码"),
    UNBIND(13, "修改手机号-解绑"),
    REBIND(14, "更换手机号-绑定新手机号"),
    COMMON(99, "通用验证码"),
    INSURE_NOTICE(110, "投保告知书"),
    INVITE_REG(111, "在线入司"),
    INSURE_VERIFY(112, "直投短险核验"),
    INSURE_SIGN_VERIFY(113, "直投签字核验");

    private int key;
    private String value;

    VerifCodeType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
