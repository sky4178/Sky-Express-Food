package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 修改分类信息
     *
     * @param category 分类实体
     */
    void update(Category category);

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO 分类分页查询数据传输对象
     * @return 分页结果
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类
     *
     * @param category 分类实体
     */
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category);

    /**
     * 根据ID删除分类
     *
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据类型查询分类列表
     *
     * @param type 分类类型
     * @return 分类列表
     */
    List<Category> list(Integer type);
}
