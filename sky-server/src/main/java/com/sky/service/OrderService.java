package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

public interface OrderService {

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO 订单提交信息
     * @return OrderSubmitVO 订单提交结果
     */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);
}
