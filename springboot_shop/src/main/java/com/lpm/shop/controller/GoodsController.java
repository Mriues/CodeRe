package com.lpm.shop.controller;

import com.alibaba.fastjson.JSONArray;
import com.lpm.shop.server.imp.AttributeServiceImpl;
import com.lpm.shop.server.imp.GoodsServiceImpl;
import com.lpm.shop.util.JsonUtils;
import com.lpm.shop.util.MapResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class GoodsController {

    @Autowired
    GoodsServiceImpl goodsService;

    @Autowired
    AttributeServiceImpl attributeService;

    @Autowired
    JsonUtils jsonUtils;

    @Autowired
    MapResultUtils mapResultUtils;

    @GetMapping("/goods")
    @ResponseBody
    public Map<String,Object> getGoodsList(@RequestParam("query") String query,
                            @RequestParam("pagenum") int pagenum,
                            @RequestParam("pagesize") int pagesize){
        List goodsList = goodsService.getGoodsList(query);
        List resultList = new ArrayList();
        if (pagesize >= goodsList.size()){
            for (int i = 0;i < goodsList.size();i++){
                resultList.add(goodsList.get(i));
            }
        }else if (pagesize < goodsList.size()){
            for (int i = 0;i < pagesize;i++){
                if (pagesize*(pagenum-1) + i > goodsList.size()-1){
                    break;
                }
                resultList.add(goodsList.get(pagesize*(pagenum-1) + i));
            }
        }
        HashMap<String,Object> goodsMap = new HashMap<>();
        goodsMap.put("goods",resultList);
        goodsMap.put("pagenum",pagenum);
        goodsMap.put("total",goodsList.size());
        HashMap<String, Object> map = mapResultUtils.resultMap(goodsMap, 200, "获取商品列表成功");
        return map;
    }

    @DeleteMapping("/goods/{id}")
    @ResponseBody
    public Map<String,Object> deleteGoodsById(@PathVariable("id") int id){
        goodsService.deleteById(id);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 204, "已删除商品");
        return map;
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map<String,Object> uploadPicture(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //判断上传文件是否为空
        if (file.isEmpty()){
            HashMap<String,Object> map = new HashMap<>();
            map.put("msg","file is empty");
            return map;
        }
        String fileName = file.getOriginalFilename();
        String filePath = "E:\\GitProject\\springboot_shop\\src\\main\\resources\\static\\tmp_uploads\\";
        String path = filePath + fileName;
        File picture = new File(path);
        //父路径不存在则创建
        if (!picture.getParentFile().exists()){
            picture.getParentFile().mkdirs();
        }
        file.transferTo(picture);
        HashMap<String,Object> data = new HashMap<>();
        String tmpPath = "tmp_uploads\\" + fileName;
        String url = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/" + tmpPath;
        data.put("tmp_path",tmpPath);
        data.put("url",url);
        HashMap<String, Object> map = mapResultUtils.resultMap(data, 201, "上传成功");
        return map;
    }

    @PostMapping("/goods")
    @ResponseBody
    public Map<String,Object> addGoods(@RequestBody String data){
        String goodsName = jsonUtils.conversionStr(data, "goods_name");
        String goodsCat = jsonUtils.conversionStr(data, "goods_cat");
        BigDecimal goodsPrice = jsonUtils.conversionDec(data, "goods_price");
        int goodsNumber = jsonUtils.conversionInt(data, "goods_number");
        int goodsWeight = jsonUtils.conversionInt(data, "goods_weight");
        String goodsIntroduce = jsonUtils.conversionStr(data, "goods_introduce");

        JSONArray pics = jsonUtils.conversionArray(data, "pics");
        for (int i = 0;i < pics.size();i++){
            String pic = (String)pics.getJSONObject(i).get("pic");
        }

        int attrId = 0;
        String attrValue = "";
        JSONArray attrs = jsonUtils.conversionArray(data, "attrs");
        for (int i = 0;i < attrs.size();i++){
            String str = attrs.getJSONObject(i).get("attr_id").toString();
            attrId = Integer.parseInt(str);
            String value = attrs.getJSONObject(i).get("attr_value").toString();
            if (i != attrs.size() - 1 && !value.equals("") ){
                attrValue += value+",";
            }else {
                attrValue += value;
            }
        }

        int catId = attributeService.getCatIdByAttrId(attrId);
        goodsService.addGoods(goodsName,goodsCat,goodsPrice,goodsNumber,goodsWeight,catId,goodsIntroduce);
        int goodsId = goodsService.getGoodsIdByName(goodsName);
        goodsService.addgoodsAttrValue(goodsId,attrId,attrValue);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "创建商品成功");
        return map;
    }

    @GetMapping("/goods/{id}")
    @ResponseBody
    public Map<String,Object> getGoodsById(@PathVariable("id") int id){
        Map goods = goodsService.getGoodsById(id);
        HashMap<String, Object> map = mapResultUtils.resultMap(goods, 200, "获取商品信息成功");
        return map;
    }

    @PutMapping("/goods/{id}")
    @ResponseBody
    public Map<String,Object> updateGoodsById(@PathVariable("id") int id,@RequestBody String data){
        String goodsName = jsonUtils.conversionStr(data, "goods_name");
        String goodsIntroduce = jsonUtils.conversionStr(data, "goods_introduce");
        BigDecimal goodsPrice = jsonUtils.conversionDec(data, "goods_price");
        int goodsWeight = jsonUtils.conversionInt(data, "goods_weight");
        int goodsNumber = jsonUtils.conversionInt(data, "goods_number");
        goodsService.updateGoodsById(id,goodsName,goodsPrice,goodsWeight,goodsNumber,goodsIntroduce);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "更新商品成功");
        return map;
    }

}
