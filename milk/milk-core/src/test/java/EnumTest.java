import com.drinks.milk.enums.insure.ChaosInsureCompanyEnum;

public class EnumTest {
    public static void main(String[] args) {
        //枚举测试
        ChaosInsureCompanyEnum e = ChaosInsureCompanyEnum.AIXIN_LIFE;
        System.err.println("公司名称："+e.getCompanyName());
        System.err.println("公司代码："+e.getCompanyCode());
        System.err.println("保险代码：" + e.getInsureCode());

        //通过insureCode查找
        ChaosInsureCompanyEnum enumConstant =  ChaosInsureCompanyEnum.getByInsureCode("DCRS");//返回枚举常量:DINGCHENG_LIFE
        String companyName = ChaosInsureCompanyEnum.getByInsureCode("huagui").getCompanyName();//获取保险公司名字
        System.err.println("枚举常量："+enumConstant);
        System.err.println("公司名称："+companyName);

        //通过companyCode查找
        String companyName1 = ChaosInsureCompanyEnum.getByCompanyCode("dajia").getCompanyName();
        System.out.println("公司名称："+companyName1);
    }
}
