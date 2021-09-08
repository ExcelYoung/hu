package com.drinks.milk.enums;

import java.util.concurrent.TimeUnit;

/**
 * @Description：锁控制枚举
 * @Author：zhangrt
 * @Date：2021/5/10 20:47
 */
public enum ChaosLockModuleEnum {

    INSURE_ORDER_EFFECT("locks:insureOrder:insureOrderEffect:", "定时任务-直投订单生效",2,TimeUnit.HOURS),

    INSURE_ORDER_INVALID("locks:insureOrder:insureOrderInvalid:", "定时任务-直投订单失效",2,TimeUnit.HOURS),

    INSURE_ORDER_ELE_DOWNLOAD("locks:insureOrder:insureOrderEleDownload:", "定时任务-直投订单下载电子保单",2,TimeUnit.HOURS),

    INSURE_ORDER_PAY_STATUS_QUERY("locks:insureOrder:insureOrderPayStatusQuery:", "定时任务-直投订单支付状态查询",40,TimeUnit.MINUTES),

    INSURE_ORDER_COM_PAY_STATUS_DEAL("locks:insureOrder:comPayStatusDeal:", "定时任务-保司支付单状态处理",4,TimeUnit.MINUTES),

    INSURE_ORDER_DINGCHENG_NET_VISIT_RESULT("locks:insureOrder:dingchengNetVisitResult:", "定时任务-鼎诚回访信息查询",4,TimeUnit.MINUTES),

    INSURE_ORDER_DINGCHENG_NET_SURRENDER_RESULT("locks:insureOrder:dingchengNetSurrenderResult:", "定时任务-鼎诚退保信息查询",4,TimeUnit.MINUTES),

    INSURE_ORDER_UNDERWRITED("locks:insureOrder:insureOrderUnderwrited:", "直投承保接口调用",3,TimeUnit.MINUTES),

    INSURE_ORDER_PAY_CALLBACK("locks:insureOrder:payCallback:", "直投支付回调接口调用",3,TimeUnit.SECONDS),


    INSURE_ORDER_PAY_REFUND("locks:insureOrder:refund:", "直投退款接口调用",3,TimeUnit.SECONDS),

    INSURE_ORDER_MANUAL_UNDERWRITING_STATUS_QUERY("locks:insureOrder:insureOrderManualUnderwritingStatusQuery:", "定时任务-直投订单人核中状态查询",40,TimeUnit.MINUTES),


    ;

    private String lockKey;

    private String name;

    private long expireTimeOut;

    private TimeUnit expireTimeUnit;

    ChaosLockModuleEnum(String lockKey, String name, long expireTimeOut, TimeUnit unit) {
        this.lockKey = lockKey;
        this.name = name;
        this.expireTimeOut = expireTimeOut;
        this.expireTimeUnit = unit;
    }

    public String getKey(String bizKey){
        if (bizKey == null){
            bizKey = "1";
        }
        return lockKey + bizKey;
    }
    public String getLockKey() {
        return lockKey;
    }

    public String getName() {
        return name;
    }

    public long getExpireTimeOut() {
        return expireTimeOut;
    }

    public TimeUnit getExpireTimeUnit() {
        return expireTimeUnit;
    }
}
