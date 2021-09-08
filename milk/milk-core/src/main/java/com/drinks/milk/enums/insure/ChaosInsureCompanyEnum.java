package com.drinks.milk.enums.insure;

public enum ChaosInsureCompanyEnum {  //枚举的enum是小写
    DINGCHENG_LIFE("DCRS", "dingcheng_life", "鼎诚人寿"),
    HUAGUI_LIFE("huagui", "huagui_life", "华贵人寿"),
    FOSUN_HEALTH("fosun", "fosun_health", "复星联合"),
    AIXIN_LIFE("aixin", "aixinlife", "爱心人寿"),
    CHINA_INSURANCE("zhonghua", "china_insurance", "中华人寿"),
    TIANAN_PI("tianan", "tianan-pi", "天安财险"),
    DAJIA("dajia", "dajia", "大家人寿"),

    ;


    //直投自用code
    private String insureCode;

    //系统code
    private String companyCode;

    //保司名
    private String companyName;

    //枚举前面的字段在构造器中定义
    ChaosInsureCompanyEnum(String insureCode, String companyCode, String companyName) {
        this.insureCode = insureCode;
        this.companyCode = companyCode;
        this.companyName = companyName;
    }

    //get方法
    public String getInsureCode() {
        return insureCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    //每个枚举都有两个特殊方法
    //static ChaosInsureCompanyEnum[] values()：返回本类所有枚举常量；
    //static ChaosInsureCompanyEnum valueOf(String name)：通过枚举常量的名字返回Direction常量，注意，这个方法与Enum类中的valueOf()方法的参数个数不同。

    //通过insureCode查找
    public static ChaosInsureCompanyEnum getByInsureCode(String insureCode) {
        ChaosInsureCompanyEnum[] types = ChaosInsureCompanyEnum.values();  //返回本类所有枚举常量
        for (ChaosInsureCompanyEnum type : types) {
            if (type.getInsureCode().equals(insureCode)) {
                return type;
            }
        }
        return null;
    }


    //通过companyCode查找
    public static ChaosInsureCompanyEnum getByCompanyCode(String companyCode) {
        ChaosInsureCompanyEnum[] types = ChaosInsureCompanyEnum.values();  //返回本类所有枚举常量
        for (ChaosInsureCompanyEnum type : types) {
            if (type.getInsureCode().equals(companyCode)) {
                return type;
            }
        }
        return null;
    }

}
