package com.lpm.shop.mapper;

import com.lpm.shop.entity.Categories;

import java.util.List;

public interface CategoriesMapper {
    List<Categories> getCategoriesList();

    void addCategories(int pid,String name,int level,int deleted);

    void updateCategories(int catId,String catName);

    void deleteCategoriesById(int catId);
}
