package com.lpm.shop.mapper;

import com.lpm.shop.entity.Menu;

import java.util.List;

public interface MenuMapper {

    List<Menu> findAll();

    List<Menu> findAllByParentId(int parentId);
}
