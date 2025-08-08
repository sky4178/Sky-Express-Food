package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
