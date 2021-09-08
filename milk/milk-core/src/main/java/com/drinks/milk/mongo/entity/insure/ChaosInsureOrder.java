package com.drinks.milk.mongo.entity.insure;
import com.drinks.milk.api.model.insure.order.ChaosInsureOrderBankAccount;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @Description: 直投订单实体
 *
 **/
@Document(collection = "chaos_insure_order")
@Data
public class ChaosInsureOrder implements Serializable {

    @Id
    private String id;

    /**
     * 订单编码
     */
    @Indexed(unique = true,background = true)
    private String orderCode;

    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 商品名称
     */
    private String goodsName;


    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 保司编码
     */
    private String companyCode;

    /**
     * 保司名称 全称
     */
    private String companyName;

    /**
     * 保司名称 简称
     */
    private String companyShortName;

    /**
     * 订单类型 1：组合订单 2：子订单
     * @see
     */
    private String orderRelationType;


    /**
     * 子订单编码（组合订单 可用）
     */
    private List<String> subOrderCodeList;

    /**
     * 订单状态
     * @see
     */
    @Indexed(background = true)
    private Integer orderStatus;

    /**
     * 订单状态描述
     */
    private String orderStatusDesc;

    /**
     * 订单状态 原因
     */
    private String orderStatusReason;

    /**
     * 保单状态
     * @see
     */
    private Integer policyStatus;

    /**
     * 保单状态描述
     */
    private String policyStatusDesc;

    /**
     * 创建时间 yyyy-MM-dd HH:mm:ss
     */
    @Indexed(background = true)
    private String createTime;

    /**
     * 应失效时间 非个险：下单第二天 个险：查询配置，T+N失效
     * 注意：拆单时 需要重新根据险种类型 查询投保配置 计算时间
     */
    private String shouldInvalidTime;

    /**
     * 生效截止日期 yyyy-MM-dd
     */
    private String effectiveEndDate;

    /**
     * 是否生效
     */
    @Indexed(background = true)
    private String effectiveStatus;

    /**
     * 投保步骤 记录投保进行到哪一步 ChaosInsureStepPageEnum
     */
    private String stepPage;
    /**
     * 是否再投一单
     */
    private String encore;

    /**
     * 删除状态 初始为0，删除后设为时间戳
     */
    @Indexed(background = true)
    private Long deletedId = 0L;
    /**
     * 银行卡信息
     */
    private ChaosInsureOrderBankAccount bankAccount;
}
