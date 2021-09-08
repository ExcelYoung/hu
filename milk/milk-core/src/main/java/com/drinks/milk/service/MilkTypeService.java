package com.drinks.milk.service;

import com.baomidou.mybatisplus.service.IService;
import com.drinks.milk.model.MilkTypeModel;

import javax.servlet.http.HttpServletResponse;

public interface MilkTypeService extends IService<MilkTypeModel> {

    /**
     * 导出牛奶种类码表
     * @date 2021/3/31
     * @author yanghuan
     */
    void exportMilkTypeModel(HttpServletResponse response);
}
