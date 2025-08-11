package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺管理")
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 更新店铺营业状态
     *
     * @param status 店铺营业状态
     * @return Result
     */
    @PutMapping("/{status}")
    @ApiOperation("更新营业状态")
    public Result updateStatus(@PathVariable Integer status) {
        log.info("更新店铺营业状态: {}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    /**
     * 获取店铺营业状态
     *
     * @return Result<Integer>
     */
    @GetMapping("/status")
    @ApiOperation("获取营业状态")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺营业状态: {}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
