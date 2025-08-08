package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {
    /**
     * 根据分类ID查询套餐数量
     *
     * @param id 分类ID
     * @return 套餐数量
     */
    @Select("SELECT COUNT(id) FROM setmeal WHERE category_id = #{id}")
    Integer countByCategoryId(Long id);
}
