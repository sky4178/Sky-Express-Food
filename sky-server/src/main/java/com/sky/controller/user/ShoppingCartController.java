package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车管理")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO 购物车数据传输对象
     * @return Result
     */
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车,商品信息为: {}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }


    /**
     * 查看购物车
     *
     * @return Result
     */
    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> list() {
        log.info("查看购物车");
        List<ShoppingCart> list = shoppingCartService.showShoppingCart();
        return Result.success(list);
    }

    /**
     * 清空购物车
     *
     * @return Result
     */
    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clear() {
        log.info("清空购物车");
        shoppingCartService.clearShoppingCart();
        return Result.success();
    }

    /**
     * 移除购物车商品
     *
     * @param shoppingCartDTO 购物车数据传输对象
     * @return Result
     */
    @PostMapping("/sub")
    @ApiOperation("移除购物车商品")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("移除购物车商品,商品信息为: {}", shoppingCartDTO);
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }
}
