package com.drinks.milk.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.drinks.milk.model.MilkTypeModel;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MilkTypeMapper extends BaseMapper<MilkTypeModel> {

    List<MilkTypeModel> selectAll();
}
