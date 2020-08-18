package com.lpm.shop.server.imp;

import com.lpm.shop.entity.Categories;
import com.lpm.shop.mapper.CategoriesMapper;
import com.lpm.shop.server.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CateServcieImpl implements CateService {


    @Autowired
    CategoriesMapper categoriesMapper;

    @Override
    public List getCategoriesList(int type) {
        List<Categories> categoriesList = categoriesMapper.getCategoriesList();
        List cateList = getZeroOfCate(categoriesList,type);
        return cateList;
    }

    @Override
    public void addCategories(int pid, String name, int level) {
        categoriesMapper.addCategories(pid,name,level,0);
    }

    @Override
    public void updateCategories(int catId, String catName) {
        categoriesMapper.updateCategories(catId,catName);
    }

    @Override
    public void deleteCategoriesById(int catId) {

        categoriesMapper.deleteCategoriesById(catId);
    }


    private List getZeroOfCate(List<Categories> categoriesList,int type){
        List cateList = new ArrayList();
        for (Categories categories : categoriesList) {
            if (categories.getCatLevel() == 0){
                HashMap<String,Object> map = new HashMap<>();
                map.put("cat_id",categories.getCatId());
                map.put("cat_name",categories.getCatName());
                map.put("cat_pid",categories.getCatPid());
                map.put("cat_level",categories.getCatLevel());
                if (categories.getCatDeleted() == 0){
                    map.put("cat_deleted",false);
                }else {
                    map.put("cat_deleted",true);
                }
                if (type != 1){
                    map.put("children",getOneOfCate(categoriesList,categories.getCatId(),type));
                }
                cateList.add(map);
            }
        }
        return cateList;
    }

    private List getOneOfCate(List<Categories> categoriesList, int catId,int type) {
        List oneList = new ArrayList();
        for (Categories categories : categoriesList) {
            if (categories.getCatLevel() == 1 && categories.getCatPid() == catId){
                HashMap<String,Object> map = new HashMap<>();
                map.put("cat_id",categories.getCatId());
                map.put("cat_name",categories.getCatName());
                map.put("cat_pid",categories.getCatPid());
                map.put("cat_level",categories.getCatLevel());
                if (categories.getCatDeleted() == 0){
                    map.put("cat_deleted",false);
                }else {
                    map.put("cat_deleted",true);
                }
                if (type != 2){
                    map.put("children",getTwoOfCate(categoriesList,categories.getCatId()));
                }
                oneList.add(map);
            }
        }
        return oneList;
    }

    private List getTwoOfCate(List<Categories> categoriesList, int catId) {
        List twoList = new ArrayList();
        for (Categories categories : categoriesList) {
            if (categories.getCatLevel() == 2 && categories.getCatPid() == catId) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("cat_id", categories.getCatId());
                map.put("cat_name", categories.getCatName());
                map.put("cat_pid", categories.getCatPid());
                map.put("cat_level", categories.getCatLevel());
                if (categories.getCatDeleted() == 0){
                    map.put("cat_deleted",false);
                }else {
                    map.put("cat_deleted",true);
                }
                twoList.add(map);
            }
        }
        return twoList;
    }
}
