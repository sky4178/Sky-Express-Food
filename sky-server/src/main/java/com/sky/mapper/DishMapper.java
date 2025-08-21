package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {
    /**
     * 根据分类ID查询菜品数量
     *
     * @param id 分类ID
     * @return 菜品数量
     */
    @Select("SELECT COUNT(id) FROM dish WHERE category_id = #{id}")
    Integer countByCategoryId(Long id);

    /**
     * 插入菜品数据
     *
     * @param dish 菜品实体
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO 分页查询条件
     * @return 分页结果
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据ID查询菜品
     *
     * @param id 菜品ID
     * @return 菜品实体
     */
    @Select("SELECT * FROM dish WHERE id = #{id}")
    Dish getById(Long id);

    /**
     * 根据ID批量删除菜品
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 更新菜品信息
     *
     * @param dish
     */
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据套餐ID查询菜品列表
     *
     * @param id 套餐ID
     * @return 菜品列表
     */
    @Select("SELECT * FROM dish WHERE id IN (SELECT dish_id FROM setmeal_dish WHERE setmeal_id = #{id})")
    List<Dish> getBySetmealId(Long id);

    /**
     * 动态条件查询菜品
     *
     * @param dish 菜品实体，包含查询条件
     * @return 菜品列表
     */
    List<Dish> list(Dish dish);

    /**
     * 根据条件统计菜品数量
     *
     * @param map 查询条件
     * @return 菜品数量
     */
    Integer countByMap(Map<String, Object> map);
}
