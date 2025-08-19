package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO 订单提交信息
     * @return OrderSubmitVO 订单提交结果
     */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO 订单支付信息
     * @return OrderPaymentVO 订单支付结果
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo 商户订单号
     */
    void paySuccess(String outTradeNo);

    /**
     * 历史订单查询
     *
     * @param page     页数
     * @param pageSize 每页大小
     * @param status   订单状态
     * @return PageResult 分页结果
     */
    PageResult pageQuery(Integer page, Integer pageSize, Integer status);

    /**
     * 查询订单详情
     *
     * @param id 订单ID
     * @return OrderVO 订单详情
     */
    OrderVO details(Long id);

    /**
     * 取消订单
     *
     * @param id 订单ID
     */
    void cancel(Long id);

    /**
     * 再来一单
     *
     * @param id 订单ID
     */
    void repetition(Long id);

    /**
     * 分页查询订单
     *
     * @param ordersPageQueryDTO 分页查询条件
     * @return PageResult 分页结果
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     *
     * @return 各个状态的订单数量统计结果
     */
    OrderStatisticsVO statistics();

    /**
     * 接单
     *
     * @param ordersConfirmDTO 接单信息
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒单
     *
     * @param ordersRejectionDTO 拒单信息
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    /**
     * 派送订单
     *
     * @param id 订单ID
     */
    void delivery(Long id);

    /**
     * 完成订单
     *
     * @param id 订单ID
     */
    void complete(Long id);
}
