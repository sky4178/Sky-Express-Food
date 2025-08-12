package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController("adminCategoryController")
@Slf4j
@RequestMapping("/admin/category")
@Api(tags = "分类管理接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param categoryDTO 分类数据传输对象
     * @return 成功结果
     */
    @PutMapping
    @ApiOperation(value = "修改分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类: {}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO 分类分页查询数据传输对象
     * @return 分页结果
     */
    @GetMapping("/page")
    @ApiOperation(value = "分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询: {}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 更新分类状态
     *
     * @param status 状态
     * @param id     分类ID
     * @return 成功结果
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "更新分类状态")
    public Result<String> updateStatus(@PathVariable Integer status, Long id) {
        log.info("更新分类状态: id={}, status={}", id, status);
        categoryService.updateStatus(id, status);
        return Result.success();
    }

    /**
     * 新增分类
     *
     * @param categoryDTO 分类数据传输对象
     * @return 成功结果
     */
    @PostMapping
    @ApiOperation(value = "新增分类")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类: {}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 成功结果
     */
    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public Result<String> deleteById(Long id) {
        log.info("删除分类: id={}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "根据类型查询分类")
    public Result<List<Category>> list(Integer type) {
        log.info("根据类型查询分类: type={}", type);
        List<Category> categories = categoryService.list(type);
        return Result.success(categories);
    }
}
