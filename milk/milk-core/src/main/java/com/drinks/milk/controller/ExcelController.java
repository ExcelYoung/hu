package com.drinks.milk.controller;

import com.drinks.milk.service.MilkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ExcelController {

    @Autowired
    private MilkTypeService milkTypeService;


    @GetMapping("/export")
    public void exportJobCateModel(HttpServletResponse response){
        milkTypeService.exportMilkTypeModel(response);
    }
}
