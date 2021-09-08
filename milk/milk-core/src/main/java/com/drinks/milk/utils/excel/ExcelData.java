package com.drinks.milk.utils.excel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExcelData  implements Serializable {


    private static final long serialVersionUID = -5893504476508065696L;
    /**
     * 表头
     */
    private List<String> titles;

    /**
     * 数据
     */
    private List<Object> rows;

    /**
     * 列属性集合
     */
    private List<String> colNames;

    /**
     * 页签名称
     */
    private String name;

}