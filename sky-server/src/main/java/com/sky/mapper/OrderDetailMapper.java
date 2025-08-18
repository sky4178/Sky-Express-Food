package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    /**
     * 批量插入订单明细
     *
     * @param orderDetailList 订单明细列表
     */
    void BatchInsert(List<OrderDetail> orderDetailList);
}
