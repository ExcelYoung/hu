package com.drinks.milk.mongo.base.model;

import com.drinks.milk.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import java.io.Serializable;


@Data
public class BaseMongoModel implements Serializable {

    @Id
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String updateTime;

    /**
     * 删除状态 初始为0，删除后设为id的值
     */
    private Long deletedId = 0L;

    public String getCreateTime() {
        if (createTime == null) {
            createTime = DateUtils.getCurrentDateTimeString();
        }
        return createTime;
    }

    public String getUpdateTime() {
        if (updateTime == null) {
            updateTime = DateUtils.getCurrentDateTimeString();
        }
        return updateTime;
    }
}
