package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增套餐，同时保存对应的菜品数据
     *
     * @param setmealDTO 套餐信息
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 向套餐表插入一条数据
        setmealMapper.insert(setmeal);

        // 获取insert语句生成的主键值
        Long setmealId = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(item -> item.setSetmealId(setmealId));
            // 向套餐菜品表插入多条数据
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    /**
     * 分页查询套餐
     *
     * @param setmealPageQueryDTO 分页查询条件
     * @return 分页结果
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        // 开启分页查询
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        // 执行查询
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        // 将查询结果转换为PageResult
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 套餐批量删除
     *
     * @param ids 套餐ID列表
     */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        // 判断套餐是否被启用
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        // 执行批量删除
        setmealMapper.deleteBatch(ids);
        // 删除套餐菜品关联数据
        setmealDishMapper.deleteBatch(ids);
    }

    /**
     * 根据ID查询套餐及其菜品信息
     *
     * @param id 套餐ID
     * @return 套餐信息及其菜品列表
     */
    @Override
    public SetmealVO getByIdWithDish(Long id) {
        // 查询套餐信息
        Setmeal setmeal = setmealMapper.getById(id);

        // 查询套餐菜品列表
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);

        // 将查询结果转换为SetmealVO
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    /**
     * 更新套餐信息及其菜品关联数据
     *
     * @param setmealDTO 套餐信息
     */
    @Override
    public void updateWithDish(SetmealDTO setmealDTO) {
        // 更新套餐信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        // 删除旧的套餐菜品关联数据
        setmealDishMapper.deleteBySetmealId(setmeal.getId());

        // 获取新的套餐菜品列表
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            // 设置套餐ID
            setmealDishes.forEach(item -> item.setSetmealId(setmeal.getId()));
            // 插入新的套餐菜品关联数据
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    /**
     * 更新套餐状态
     *
     * @param id     套餐ID
     * @param status 新状态
     */
    @Override
    public void updateStatus(Long id, Integer status) {

        //起售套餐时，判断套餐内是否有停售菜品，有停售菜品提示"套餐内包含未启售菜品，无法启售"
        if (status == StatusConstant.ENABLE) {
            List<Dish> dishList = dishMapper.getBySetmealId(id);
            if (dishList != null && !dishList.isEmpty()) {
                dishList.forEach(dish -> {
                    if (StatusConstant.DISABLE == dish.getStatus()) {
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }
        }
        // 构建Setmeal对象
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        // 执行更新操作
        setmealMapper.update(setmeal);
    }

    /**
     * 根据条件查询套餐列表
     *
     * @param setmeal 查询条件
     * @return 套餐列表
     */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.list(setmeal);
    }

    /**
     * 根据套餐ID查询套餐下的菜品列表
     *
     * @param id 套餐ID
     * @return 套餐下的菜品列表
     */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealDishMapper.getDishItemBySetmealId(id);

    }

}
