package com.lpm.shop.mapper;

import com.lpm.shop.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsMapper {

    List<Goods> getGoodsList(@Param("query") String query);

    void deleteById(int id);

    void addGoods(String goodsName, int oneId, int twoId, int threeId, BigDecimal price,int number,int weight,String introduce,int catId,int addTime,int updTime);

    Goods getGoodsIdByName(String goodsName);

    void addgoodsAttrValue(int goodsId,int attrId,String attrValue);

    Goods getGoodsById(int id);

    void updateGoodsById(int goodsId,String goodsName,BigDecimal goodsPrice,int goodsWeight,int goodsNumber,String introduce,int updTime);

}
