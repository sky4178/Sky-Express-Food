package com.sky.service;

import com.sky.dto.SetmealDTO;

public interface SetmealService {
    /**
     * 新增套餐，同时保存对应的菜品数据
     *
     * @param setmealDTO 套餐信息
     */
    void saveWithDish(SetmealDTO setmealDTO);
}
