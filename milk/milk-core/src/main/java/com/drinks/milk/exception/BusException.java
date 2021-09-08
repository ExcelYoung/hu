package com.drinks.milk.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: BusException
 * @Package com.insuranceman.common.exception   
 * @Description: 自定义异常
 * @author: 李萌    
 * @date:   2018年7月8日 上午09:16:44   
 * @version V1.0 
 * @Copyright: 2018 壹心壹翼科技  All rights reserved.
 */
public class BusException extends Exception {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(BusException.class);
    private String msg;
    private int code = 500;
    
    public BusException(String msg) {
		super(msg);
		this.msg = msg;
		logger.error("BusException:"+msg);
	}
	
	public BusException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
		logger.error("BusException:"+msg+";Throwable:"+e.getMessage());
	}
	
	public BusException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
		logger.error("BusException:"+msg+","+code);
	}
	
	public BusException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
		logger.error("BusException:"+msg+","+code+";Throwable:"+e.getMessage());
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
