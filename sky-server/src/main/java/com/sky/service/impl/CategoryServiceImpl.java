package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 修改分类信息
     *
     * @param categoryDTO 分类数据传输对象
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        // 拷贝属性
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        // 设置修改时间和修改人
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        // 执行更新操作
        categoryMapper.update(category);
    }

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO 分类分页查询数据传输对象
     * @return 分页结果
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        // 开启分页
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        // 执行分页查询
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        // 将查询结果转换为PageResult
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 更新分类状态
     *
     * @param id     分类ID
     * @param status 状态
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        Category category = Category.builder().
                id(id).
                status(status).
                updateTime(LocalDateTime.now()).
                updateUser(BaseContext.getCurrentId()).
                build();
        // 执行更新操作
        categoryMapper.update(category);
    }

    @Override
    public void save(CategoryDTO categoryDTO) {
        // 拷贝属性
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        // 设置状态为禁用
        category.setStatus(StatusConstant.DISABLE);

        // 设置创建时间和创建人
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        // 执行插入操作
        categoryMapper.insert(category);
    }

    /**
     * 根据ID删除分类
     *
     * @param id 分类ID
     */
    @Override
    public void deleteById(Long id) {
        //查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        Integer count = dishMapper.countByCategoryId(id);
        if (count > 0) {
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        count = setmealMapper.countByCategoryId(id);
        if (count > 0) {
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        //删除分类数据
        categoryMapper.deleteById(id);
    }

    /**
     * 根据类型查询分类列表
     *
     * @param type 分类类型
     * @return 分类列表
     */
    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
