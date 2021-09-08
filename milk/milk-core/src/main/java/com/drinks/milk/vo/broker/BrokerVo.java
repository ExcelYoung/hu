package com.drinks.milk.vo.broker;

import java.util.HashMap;
import java.util.Map;

public class BrokerVo {
	
    /**
     * 昵称
     */
	private String nickName;
    /**
     * 真实姓名
     */
	private String name;
    /**
     * 头像
     */
	private String headImg;
    /**
     * 个人简介
     */
	private String individual;
    /**
     * 公司简介
     */
	private String companyPro;
	
	/**
	 * 个人风采
	 */
	private String[] personImgs ;
	/**
	 * 注册时间
	 */
	private String appRegTime ;
	/**
	 * 级别
	 */
	private String brokeGrade ;
	/**
	 * 代理人编码
	 */
	private String brokerCode ;
	
	/**
	 * 微信号二维码
	 * @return
	 */
    private String wechat;
    
    private Integer gender;
    
    private String pinCodes ;
    
    private String postcardCode;
    
    private Integer insId;
    
    private String companyName;
    
    private Map<String, Object> map = new HashMap<>();
    
    private String id ;
//    注册手机号 
    private String regMobile;
//    壹心推荐人
    private String inviteName;
//  壹心推荐人
  private String inviteMobile;
//    执业证书编号
    private String licenseNo ;
//    执业地区
    private String practiceArea;
//    所属公司
    private String company;
//    职业信息：
//    壹心工号
//    入司时间
    private String employDate;
//    入司报备电话
    private String mobile;
//    所属机构
    private String orgName;
//    营销服务区
    private String mkServiceName;
//    营销服务区总监
    private String firectorName;
//    电子合同
    private String htUrl ;
    //代理人类型
    private Integer brokerType;
    //代理人数据来源
    private Integer dataSource;
    
	public Integer getBrokerType() {
		return brokerType;
	}

	public void setBrokerType(Integer brokerType) {
		this.brokerType = brokerType;
	}

	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	public String getInviteMobile() {
		return inviteMobile;
	}

	public void setInviteMobile(String inviteMobile) {
		this.inviteMobile = inviteMobile;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getMkServiceName() {
		return mkServiceName;
	}

	public void setMkServiceName(String mkServiceName) {
		this.mkServiceName = mkServiceName;
	}

	public String getFirectorName() {
		return firectorName;
	}

	public void setFirectorName(String firectorName) {
		this.firectorName = firectorName;
	}

	public String getHtUrl() {
		return htUrl;
	}

	public void setHtUrl(String htUrl) {
		this.htUrl = htUrl;
	}

	public String getBrokerCode() {
		return brokerCode;
	}

	public String getInviteName() {
		return inviteName;
	}

	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getPracticeArea() {
		return practiceArea;
	}

	public void setPracticeArea(String practiceArea) {
		this.practiceArea = practiceArea;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmployDate() {
		return employDate;
	}

	public void setEmployDate(String employDate) {
		this.employDate = employDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setBrokerCode(String brokerCode) {
		this.brokerCode = brokerCode;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getIndividual() {
		return individual;
	}

	public void setIndividual(String individual) {
		this.individual = individual;
	}

	public String getCompanyPro() {
		return companyPro;
	}

	public void setCompanyPro(String companyPro) {
		this.companyPro = companyPro;
	}
	
	public String[] getPersonImgs() {
		return personImgs;
	}

	public void setPersonImgs(String[] personImgs) {
		this.personImgs = personImgs;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getAppRegTime() {
		return appRegTime;
	}

	public void setAppRegTime(String appRegTime) {
		this.appRegTime = appRegTime;
	}

	public String getBrokeGrade() {
		return brokeGrade;
	}

	public void setBrokeGrade(String brokeGrade) {
		this.brokeGrade = brokeGrade;
	}

	public String getPinCodes() {
		return pinCodes;
	}

	public void setPinCodes(String pinCodes) {
		this.pinCodes = pinCodes;
	}

	public String getRegMobile() {
		return regMobile;
	}

	public void setRegMobile(String regMobile) {
		this.regMobile = regMobile;
	}

	public String getPostcardCode() {
		return postcardCode;
	}

	public void setPostcardCode(String postcardCode) {
		this.postcardCode = postcardCode;
	}

	public Integer getInsId() {
		return insId;
	}

	public void setInsId(Integer insId) {
		this.insId = insId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
