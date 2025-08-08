package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
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

    /**
     * 插入套餐数据
     *
     * @param setmeal 套餐实体
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);
}
