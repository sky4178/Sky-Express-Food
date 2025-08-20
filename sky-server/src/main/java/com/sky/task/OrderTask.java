package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类，用于处理订单相关的定时任务
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 每分钟执行一次，处理超时订单
     */
    @Scheduled(cron = "0 * * * * *")
    public void processTimeOutOrder() {
        // 处理超时订单的逻辑
        log.info("定时任务：处理超时订单{}", LocalDateTime.now());
        // 查询所有待支付订单，超过15分钟未支付的订单
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTime(Orders.PENDING_PAYMENT, time);
        if (ordersList != null && !ordersList.isEmpty()) {
            for (Orders order : ordersList) {
                // 处理超时订单的逻辑
                order.setStatus(Orders.CANCELLED); // 假设将超时订单状态改为已取消
                order.setCancelReason("订单超时未支付，已自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    /**
     * 每天凌晨1点执行，处理配送订单
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder() {
        log.info("定时任务：处理配送订单{}", LocalDateTime.now());
        // 处理配送订单的逻辑
        LocalDateTime time = LocalDateTime.now().plusHours(-1);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTime(Orders.DELIVERY_IN_PROGRESS, time);
        if (ordersList != null && !ordersList.isEmpty()) {
            for (Orders order : ordersList) {
                // 处理配送订单的逻辑
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
