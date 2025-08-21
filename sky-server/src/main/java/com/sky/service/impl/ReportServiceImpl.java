package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkspaceService workspaceService;

    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        // 当前集合用于存放从begin到end日期
        List<LocalDate> dateList = new ArrayList<>();
        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)) {
            dateList.add(date);
        }
        // 获取营业额数据
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalDateTime.MIN.toLocalTime());
            LocalDateTime endTime = LocalDateTime.of(date, LocalDateTime.MAX.toLocalTime());
            Map map = new HashMap();
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnoverList.add(turnover != null ? turnover : 0.0);
        }
        // 返回结果
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        // 当前集合用于存放从begin到end日期
        List<LocalDate> dateList = new ArrayList<>();
        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)) {
            dateList.add(date);
        }
        // 获取用户数据
        List<Integer> totalUserList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalDateTime.MIN.toLocalTime());
            LocalDateTime endTime = LocalDateTime.of(date, LocalDateTime.MAX.toLocalTime());
            Map map = new HashMap();
            map.put("endTime", endTime);
            Integer userCount = userMapper.countUserByMap(map);
            totalUserList.add(userCount != null ? userCount : 0);
            map.put("beginTime", beginTime);
            Integer newUserCount = userMapper.countUserByMap(map);
            newUserList.add(newUserCount != null ? newUserCount : 0);
        }
        // 返回结果
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }

    /**
     * 订单统计接口
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 订单统计结果
     */
    @Override
    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        // 当前集合用于存放从begin到end日期
        List<LocalDate> dateList = new ArrayList<>();
        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)) {
            dateList.add(date);
        }
        // 获取订单数据
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();
        int totalOrderCount = 0;
        int validOrderCount = 0;
        double orderCompletionRate = 0.0;
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalDateTime.MIN.toLocalTime());
            LocalDateTime endTime = LocalDateTime.of(date, LocalDateTime.MAX.toLocalTime());

            // 统计订单总数
            Integer orderCount = getOrderCount(beginTime, endTime, null);
            orderCountList.add(orderCount);
            totalOrderCount += orderCount;

            // 统计有效订单数
            Integer validOrderCountForDay = getOrderCount(beginTime, endTime, Orders.COMPLETED);
            validOrderCountList.add(validOrderCountForDay);
            validOrderCount += validOrderCountForDay;
        }

        if (totalOrderCount > 0) {
            orderCompletionRate = (double) validOrderCount / totalOrderCount;
        }

        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    /**
     * 销量前十统计接口
     *
     * @return 销量前十统计结果
     */
    @Override
    public SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end) {
        //  获取销量前十数据
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalDateTime.MIN.toLocalTime());
        LocalDateTime endTime = LocalDateTime.of(end, LocalDateTime.MAX.toLocalTime());
        List<GoodsSalesDTO> top10Data = orderMapper.getTop10(beginTime, endTime);
        List<String> nameList = top10Data.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numberList = top10Data.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        // 返回结果
        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
                .build();
    }

    /**
     * 导出营业数据
     *
     * @param response HttpServletResponse对象，用于导出文件
     */
    @Override
    public void exportBusinessData(HttpServletResponse response) {
        //1.查询数据库获取营业数据
        LocalDate dateBegin = LocalDate.now().minusDays(30);
        LocalDate dateEnd = LocalDate.now().minusDays(1);
        BusinessDataVO businessData = workspaceService.getBusinessData(dateBegin.atTime(LocalTime.MIN), dateEnd.atTime(LocalTime.MAX));

        //2.通过POI等工具将数据写入Excel文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");

        try {
            // 使用POI读取Excel模板
            XSSFWorkbook excel = new XSSFWorkbook(is);

            //填充数据--时间
            XSSFSheet sheet = excel.getSheet("Sheet1");
            sheet.getRow(1).getCell(1).setCellValue("时间：" + dateBegin + " 至 " + dateEnd);

            //填充数据--概览数据
            XSSFRow row = sheet.getRow(3);
            row.getCell(2).setCellValue(businessData.getTurnover());
            row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessData.getNewUsers());
            row = sheet.getRow(4);
            row.getCell(2).setCellValue(businessData.getValidOrderCount());
            row.getCell(4).setCellValue(businessData.getUnitPrice());

            //填充数据--明细数据
            for (int i = 0; i < 30; i++) {
                LocalDate date = dateBegin.plusDays(i);
                // 查询指定日期的营业数据
                BusinessDataVO Data = workspaceService.getBusinessData(date.atTime(LocalTime.MIN), date.atTime(LocalTime.MAX));
                // 获取当前行
                row = sheet.getRow(7 + i);
                // 填充日期
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(Data.getTurnover());
                row.getCell(3).setCellValue(Data.getValidOrderCount());
                row.getCell(4).setCellValue(Data.getOrderCompletionRate());
                row.getCell(5).setCellValue(Data.getUnitPrice());
                row.getCell(6).setCellValue(Data.getNewUsers());
            }

            //通过输出流将Excel文件写入到响应中
            ServletOutputStream os = response.getOutputStream();
            excel.write(os);
            //关闭资源
            os.close();
            excel.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定时间段内的订单数量
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param status    订单状态（可选）
     * @return 订单数量
     */
    private Integer getOrderCount(LocalDateTime beginTime, LocalDateTime endTime, Integer status) {
        Map<String, Object> map = new HashMap<>();
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        if (status != null) {
            map.put("status", status);
        }
        Integer count = orderMapper.countByMap(map);
        return count != null ? count : 0;
    }
}
