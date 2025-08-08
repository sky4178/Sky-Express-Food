package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {
    /**
     * 新增菜品，同时保存对应的口味数据
     *
     * @param dishDTO 菜品信息
     */
    void saveWithFlavor(DishDTO dishDTO);
}
