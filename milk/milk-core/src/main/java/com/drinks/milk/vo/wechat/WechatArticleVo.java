package com.drinks.milk.vo.wechat;

import java.io.Serializable;
import java.util.List;

//微信文章展示实体
public class WechatArticleVo implements Serializable {

	private static final long serialVersionUID = 8774664648351893367L;
	private String title;
	private String date;
	private String headImg;
	private String authHeadImg;
	private String author;
	private List<String> content;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getAuthHeadImg() {
		return authHeadImg;
	}
	public void setAuthHeadImg(String authHeadImg) {
		this.authHeadImg = authHeadImg;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<String> getContent() {
		return content;
	}
	public void setContent(List<String> content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
		
}
