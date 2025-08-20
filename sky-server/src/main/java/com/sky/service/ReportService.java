package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    /**
     * 营业额统计接口
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 营业额统计结果
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * 用户统计接口
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 用户统计结果
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    /**
     * 订单统计接口
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 订单统计结果
     */
    OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

    /**
     * 销量前十统计接口
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 销量前十统计结果
     */
    SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end);
}
