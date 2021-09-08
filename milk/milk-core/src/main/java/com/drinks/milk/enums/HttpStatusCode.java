/**
 * 
 */
package com.drinks.milk.enums;
/**
 * @Title: HttpStatusCodeEnum.java 
 * @Package com.insuranceman.rest.enums    
 * @Description:HTTP状态码定义
 * @author: 李萌    
 * @date:    2018年7月12日   
 * @version V1.0 
 * @Copyright: 2018 壹心壹翼科技  All rights reserved.
 */
public enum HttpStatusCode {
	SUCCESS("请求成功", 200), 
	ILLEGAL("调用不合法",400), 
	UNLOGIN("用户未登录",401), 
	UNCERTIFIED("用户未认证",402), 
	ILLEGALUSERINFO("非法用户信息",403), 
	LOST("资源不存在",404), 
	EXCEPTION("服务器处理异常",500), 
	FAIL("请求失败", 999);  
    private String desc;  
    private int code;  
    private HttpStatusCode(String name, int index) {  
        this.desc = name;  
        this.code = index;  
    }  
    public static String getName(int index) {  
        for (HttpStatusCode c : HttpStatusCode.values()) {  
            if (c.getCode() == index) {  
                return c.desc;  
            }  
        }  
        return null;  
    }
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}  
}
