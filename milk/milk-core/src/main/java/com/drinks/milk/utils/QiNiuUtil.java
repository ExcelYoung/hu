package com.drinks.milk.utils;

import com.drinks.milk.configuration.ConfigService;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class QiNiuUtil {

	private static Logger logger = LoggerFactory.getLogger(QiNiuUtil.class);

    @Autowired
	ConfigService configService;
	/**
	 * 获取七牛上传文件upToken
	 * 
	 *  accessKey
	 *  secretKey
	 *  bucket
	 * @return
	 */
	public static String getUpToken(String accessKey,String secretKey,String bucket) {
		String upToken = "";
		try {
			Auth auth = Auth.create(accessKey, secretKey);
			upToken = auth.uploadToken(bucket);
		} catch (Exception e) {
			logger.error("QiNiuUtil error " + e);
		}
		return upToken;
	}
}
