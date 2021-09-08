package com.drinks.milk.model;


import com.drinks.milk.annotation.Excel;
import lombok.Data;

@Data
public class MilkTypeModel extends BaseModel {
    /**
     *名字
     */
    @Excel(name = "名字")
    private String name;
    /**
     *价格
     */
    @Excel(name = "价格")
    private String price;
}
