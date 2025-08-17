package com.sky.mapper;


import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 查询购物车列表
     *
     * @param shoppingCart 购物车对象
     * @return 购物车列表
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 根据购物车ID更新购物车数量
     *
     * @param cart 购物车对象
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart cart);

    /**
     * 插入新的购物车记录
     *
     * @param shoppingCart 购物车对象
     */
    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, " +
            "dish_flavor, number, amount, create_time) " +
            "values (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, " +
            "#{number}, #{amount}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    /**
     * 清空购物车
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void clearByUserId(Long userId);
}
