package com.drinks.milk.api.model.insure.order;



import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 银行缴费账户
 **/
@Data
public class ChaosInsureOrderBankAccount implements Serializable {

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 开户行省
     */
    private String bankProvinceCode;

    /**
     * 开户行省名称
     */
    private String bankProvinceName;

    /**
     * 开户行市
     */
    private String bankCityCode;

    /**
     * 开户行市名称
     */
    private String bankCityName;

    /**
     * 区编码
     */
    private String areaCode;

    /**
     * 区名称
     */
    private String areaName;

    /**
     * 银行卡号
     */
    private String bankAccountNo;

    /**
     * 账户类型(默认借记卡)
     */
    private String accountType = "debitCard";

    /**
     * 银行预留手机号
     */
    private String cellPhone;
}
