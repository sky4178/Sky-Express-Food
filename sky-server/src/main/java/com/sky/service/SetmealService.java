package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

public interface SetmealService {
    /**
     * 新增套餐，同时保存对应的菜品数据
     *
     * @param setmealDTO 套餐信息
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 分页查询套餐信息
     *
     * @param setmealPageQueryDTO 分页查询条件
     * @return 分页结果
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
}
