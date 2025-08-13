package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户端菜品浏览接口
 */
@RestController("userDishController")
@Slf4j
@RequestMapping("/user/dish")
@Api(tags = "用户端菜品浏览接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    @Cacheable(value = "dishCache", key = "#categoryId")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("根据分类id查询菜品, categoryId: {}", categoryId);
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        List<DishVO> dishList = dishService.listWithFlavor(dish);
        return Result.success(dishList);
    }
}
