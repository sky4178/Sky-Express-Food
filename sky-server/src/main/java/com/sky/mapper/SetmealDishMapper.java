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

    /**
     * 根据菜品ID列表查询对应的套餐ID列表
     *
     * @param dishIds 菜品ID列表
     * @return 套餐ID列表
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 根据套餐ID列表删除套餐菜品关系数据
     *
     * @param ids 套餐ID列表
     */
    void deleteBatch(List<Long> ids);
}
