package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {
    /**
     * 新增菜品，同时保存对应的口味数据
     *
     * @param dishDTO 菜品信息
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 分页查询菜品信息
     *
     * @param dishPageQueryDTO 分页查询条件
     * @return 分页结果
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
