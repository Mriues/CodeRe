package com.lpm.shop.server;

import java.util.List;

public interface CateService {

    List getCategoriesList(int type);

    void addCategories(int pid,String name,int id);

    void updateCategories(int catId,String catName);

    void deleteCategoriesById(int catId);
}
