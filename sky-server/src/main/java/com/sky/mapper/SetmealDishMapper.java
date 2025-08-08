package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 批量插入套餐菜品关系数据
     *
     * @param setmealDishes 套餐菜品关系列表
     */
    void insertBatch(List<SetmealDish> setmealDishes);
}
