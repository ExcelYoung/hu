package com.drinks.milk.controller.qiniu;

import com.drinks.milk.configuration.ConfigService;
import com.drinks.milk.constant.CommonConstant;
import com.drinks.milk.utils.QiNiuUtil;


import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QiniuController {
    private static Logger logger = LoggerFactory.getLogger(QiniuController.class);
  /*private static final String accessKey = "auQ2QevhqcuPTBOiYHa8Tj9LqJf1x2Er0EN1nLfu";
    private static final String secretKey = "nUlTJeyjC6e4gW0UOlRFJMTg-QY56FIRcHLj-7W3";
    private static final String bucket = "yximg";*/
    @Autowired
    ConfigService configService;

    @PostMapping("/qiniu/token")
    public String getUpToken() {
        String upToken = QiNiuUtil.getUpToken(configService.getString(CommonConstant.QiniuAuth.QINIU_ACCESSKEY),
                configService.getString(CommonConstant.QiniuAuth.QINIU_SECRETKEY),
                configService.getString(CommonConstant.QiniuAuth.QINIU_BUCKET));
        return upToken;
    }
}
