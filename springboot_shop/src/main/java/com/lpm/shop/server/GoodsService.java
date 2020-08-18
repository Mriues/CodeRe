package com.lpm.shop.server;

import com.lpm.shop.entity.Goods;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GoodsService {

    List getGoodsList(String query);

    void deleteById(int id);

    void addGoods(String name, String catId, BigDecimal price, int number, int weight,int cateId, String introduce);

    int getGoodsIdByName(String name);

    void addgoodsAttrValue(int goodsId,int attrId,String attrValue);

    Map getGoodsById(int id);

    void updateGoodsById(int goodsId,String goodsName,BigDecimal goodsPrice,int goodsWeight,int goodsNumber,String introduce);
}
