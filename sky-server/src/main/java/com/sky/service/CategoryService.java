package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    /**
     * 修改分类信息
     *
     * @param categoryDTO 分类数据传输对象
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO 分类分页查询数据传输对象
     * @return 分页结果
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 更新分类状态
     *
     * @param id     分类ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 新增分类
     *
     * @param categoryDTO 分类数据传输对象
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 根据ID删除分类
     *
     * @param id 分类ID
     */
    void deleteById(Long id);

    /**
     * 根据类型查询分类列表
     *
     * @param type 分类类型
     * @return 分类列表
     */
    List<Category> list(Integer type);
}
