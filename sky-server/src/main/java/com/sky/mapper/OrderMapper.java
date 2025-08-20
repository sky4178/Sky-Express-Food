package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单信息
     *
     * @param orders 订单信息
     */
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber 订单号
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders 订单信息
     */
    void update(Orders orders);

    /**
     * 分页查询订单
     *
     * @param ordersPageQueryDTO 订单分页查询信息
     * @return 订单分页查询结果
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据订单ID查询订单
     *
     * @param id 订单ID
     * @return 订单信息
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 统计指定状态的订单数量
     *
     * @param status 订单状态
     * @return 订单数量
     */
    @Select("select count(*) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 根据状态和订单时间查询订单
     *
     * @param status    订单状态
     * @param orderTime 订单时间
     * @return 符合条件的订单列表
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime orderTime);
}
