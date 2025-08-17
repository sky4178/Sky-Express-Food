package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
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
    void save(AddressBook addressBook);

    /**
     * 根据ID查询地址
     *
     * @param id 地址ID
     * @return 地址簿对象
     */
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
    void deleteById(Long id);

    /**
     * 设置默认地址
     *
     * @param addressBook 地址簿对象
     */
    void setDefault(AddressBook addressBook);
}
