package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入菜品口味数据
     *
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 批量删除菜品口味数据
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据菜品ID查询对应的口味数据
     *
     * @param id 菜品ID
     * @return 口味列表
     */
    @Select("SELECT * FROM dish_flavor WHERE dish_id = #{id}")
    List<DishFlavor> getByDishId(Long id);

    /**
     * 根据菜品ID删除对应的口味数据
     *
     * @param id
     */
    @Delete("DELETE FROM dish_flavor WHERE dish_id = #{id}")
    void deleteByDishId(Long id);
}
