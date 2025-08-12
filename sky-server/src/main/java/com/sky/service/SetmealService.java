package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

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

    /**
     * 批量删除套餐信息
     *
     * @param ids 套餐ID列表
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据ID查询套餐信息及对应的菜品数据
     *
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * 修改套餐信息，同时更新对应的菜品数据
     *
     * @param setmealDTO 套餐信息
     */
    void updateWithDish(SetmealDTO setmealDTO);

    /**
     * 更新套餐状态
     *
     * @param id     套餐ID
     * @param status 状态值
     */
    void updateStatus(Long id, Integer status);

    /**
     * 根据条件查询套餐列表
     *
     * @param setmeal 查询条件
     * @return 套餐列表
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐ID查询套餐下的菜品列表
     *
     * @param id 套餐ID
     * @return 套餐下的菜品列表
     */
    List<DishItemVO> getDishItemById(Long id);
}
