package com.sky.mapper;

import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据套餐ID查询对应的套餐菜品列表
     *
     * @param id 套餐ID
     * @return 套餐菜品列表
     */
    @Select("SELECT * FROM setmeal_dish WHERE setmeal_id = #{id}")
    List<SetmealDish> getBySetmealId(Long id);

    /**
     * 根据套餐ID删除对应的套餐菜品关系数据
     *
     * @param id 套餐ID
     */
    @Delete("DELETE FROM setmeal_dish WHERE setmeal_id = #{id}")
    void deleteBySetmealId(Long id);

    /**
     * 根据套餐ID查询对应的菜品信息
     *
     * @param id 套餐ID
     * @return 菜品信息列表
     */
    @Select("SELECT sd.name,sd.copies,d.image,d.description " +
            "from setmeal_dish sd left join dish d on " +
            "sd.dish_id = d.id where sd.setmeal_id = #{id}")
    List<DishItemVO> getDishItemBySetmealId(Long id);
}
