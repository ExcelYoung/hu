package com.drinks.milk.entity;

import com.drinks.milk.model.BaseModel;
import lombok.Data;



@Data
public class MilkType  extends BaseModel{
    /**
     *名字
     */
    private String name;
    /**
     *价格
     */
    private String price;
}