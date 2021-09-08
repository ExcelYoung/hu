package com.drinks.milk.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.drinks.milk.mapper.MilkTypeMapper;
import com.drinks.milk.model.MilkTypeModel;
import com.drinks.milk.utils.excel.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Service
public class MilkTypeServiceImpl extends ServiceImpl<MilkTypeMapper, MilkTypeModel> implements MilkTypeService {

    @Autowired
    private MilkTypeMapper milkTypeMapper;
    @Override
    public void exportMilkTypeModel(HttpServletResponse response) {
        List<MilkTypeModel> list = new ArrayList<>();
        //list.add(new MilkTypeModel());
        list = milkTypeMapper.selectAll();
        ExcelUtils<MilkTypeModel> excelUtils = new ExcelUtils<>(MilkTypeModel.class);
        try {
            excelUtils.writeExcel(list, "牛奶种类码表", response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
