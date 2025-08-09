package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

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

    /**
     * 批量删除菜品信息
     *
     * @param ids 菜品ID列表
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据ID查询菜品信息及对应的口味数据
     *
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 修改菜品信息，同时更新对应的口味数据
     *
     * @param dishDTO 菜品信息
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 更新菜品状态
     *
     * @param id     菜品ID
     * @param status 状态值
     */
    void updateStatus(Long id, Integer status);
}
