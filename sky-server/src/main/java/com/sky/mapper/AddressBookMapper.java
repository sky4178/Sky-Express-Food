package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.AddressBook;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    /**
     * 查询当前登录用户的所有地址信息
     *
     * @param addressBook 包含用户ID的地址簿对象
     * @return 地址簿列表
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 新增地址
     *
     * @param addressBook 地址簿对象
     */
    @Insert("insert into address_book (user_id, consignee, sex, phone, province_code, province_name, city_code, " +
            "city_name, district_code, district_name, detail, label, is_default) " +
            "VALUES (#{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode}," +
            "#{cityName},#{districtCode},#{districtName},#{detail},#{label},#{isDefault})")
    void insert(AddressBook addressBook);

    /**
     * 根据ID查询地址
     *
     * @param id 地址ID
     * @return 地址簿对象
     */
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);

    /**
     * 根据ID修改地址
     *
     * @param addressBook 地址簿对象
     */
    void update(AddressBook addressBook);

    /**
     * 根据ID删除地址
     *
     * @param id 地址ID
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据用户id修改是否默认地址
     *
     * @param addressBook 地址簿对象
     */
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);
}
