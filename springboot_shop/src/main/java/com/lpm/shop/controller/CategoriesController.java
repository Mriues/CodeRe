package com.lpm.shop.controller;

import com.lpm.shop.server.imp.CateServcieImpl;
import com.lpm.shop.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CategoriesController {

    @Autowired
    CateServcieImpl cateServcie;

    @Autowired
    JsonUtils jsonUtils;

    @GetMapping("/categories")
    @ResponseBody
    public Map getCategoriesList(@RequestParam(value = "type",required = false,defaultValue = "3") int type,
                                 @RequestParam(value = "pagenum",required = false,defaultValue = "0") int pagenum,
                                 @RequestParam(value = "pagesize",required = false,defaultValue = "0") int pagesize){
        if (pagenum != 0 && pagesize != 0){
            List categoriesList = cateServcie.getCategoriesList(type);
            List resultList = new ArrayList();
            if (pagesize >= categoriesList.size()){
                for (int i = 0;i < categoriesList.size();i++){
                    resultList.add(categoriesList.get(i));
                }
            }else if (pagesize < categoriesList.size()){
                for (int i = 0;i < pagesize;i++){
                    if (pagesize*(pagenum-1) + i > categoriesList.size()-1){
                        break;
                    }
                    resultList.add(categoriesList.get(pagesize*(pagenum-1) + i));
                }
            }
            HashMap<String,Object> map = new HashMap<>();
            HashMap<String,Object> metaMap = new HashMap<>();
            HashMap<String,Object> resultMap = new HashMap<>();
            resultMap.put("result",resultList);
            resultMap.put("pagenum",pagenum);
            resultMap.put("pagesize",pagesize);
            resultMap.put("total",categoriesList.size());
            metaMap.put("status",200);
            metaMap.put("msg","获取商品列表成功！");
            map.put("meta",metaMap);
            map.put("data",resultMap);
            return map;
        }else {
            List categoriesList = cateServcie.getCategoriesList(type);
            HashMap<String,Object> map = new HashMap<>();
            HashMap<String,Object> metaMap = new HashMap<>();
            metaMap.put("status",200);
            metaMap.put("msg","获取商品列表成功！");
            map.put("meta",metaMap);
            map.put("data",categoriesList);
            return map;
        }
    }

    @PostMapping("/categories")
    @ResponseBody
    public Map<String,Object> addCategories(@RequestBody String data){
        int cat_pid = jsonUtils.conversionInt(data, "cat_pid");
        String cat_name = jsonUtils.conversionStr(data, "cat_name");
        int cat_level = jsonUtils.conversionInt(data, "cat_level");
        cateServcie.addCategories(cat_pid,cat_name,cat_level);
        HashMap<String,Object> metaMap = new HashMap<>();
        HashMap<String,Object> map = new HashMap<>();
        metaMap.put("status",201);
        metaMap.put("msg","添加分类成功！");
        map.put("meta",metaMap);
        return map;
    }

    @PutMapping("/categories/{catId}")
    @ResponseBody
    public Map<String,Object> updateCategoriesById(@PathVariable("catId") int catId,@RequestBody String data){
        String catName = jsonUtils.conversionStr(data, "cat_name");
        cateServcie.updateCategories(catId,catName);
        HashMap<String,Object> metaMap = new HashMap<>();
        HashMap<String,Object> map = new HashMap<>();
        metaMap.put("status",201);
        metaMap.put("msg","更新分类成功！");
        map.put("meta",metaMap);
        return map;
    }

    @DeleteMapping("/categories/{catId}")
    @ResponseBody
    public Map<String,Object> deleteCategoriesById(@PathVariable("catId") int catId){
        cateServcie.deleteCategoriesById(catId);
        HashMap<String,Object> metaMap = new HashMap<>();
        HashMap<String,Object> map = new HashMap<>();
        metaMap.put("status",204);
        metaMap.put("msg","删除分类成功！");
        map.put("meta",metaMap);
        return map;
    }

}
