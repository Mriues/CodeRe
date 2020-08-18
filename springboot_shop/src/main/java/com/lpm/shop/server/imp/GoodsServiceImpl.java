package com.lpm.shop.server.imp;

import com.lpm.shop.entity.Goods;
import com.lpm.shop.mapper.GoodsMapper;
import com.lpm.shop.server.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;


    @Override
    public List getGoodsList(String query) {
        List<Goods> goodsList = goodsMapper.getGoodsList(query);
        List list = new ArrayList();
        for (Goods goods : goodsList) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("goods_id",goods.getId());
            map.put("goods_name",goods.getName());
            map.put("goods_price",goods.getPrice());
            map.put("goods_weight",goods.getWeight());
            map.put("goods_state",goods.getState());
            map.put("add_time",goods.getAddTime());
            map.put("upd_time",goods.getUpTime());
            map.put("hot_mumber",goods.getHotNumber());
            map.put("is_promote",goods.getIsPromote());
            list.add(map);
        }
        return list;
    }

    @Override
    public void deleteById(int id) {
        goodsMapper.deleteById(id);
    }

    @Override
    public void addGoods(String name, String catId, BigDecimal price, int number, int weight,int cateId, String introduce) {
        String[] split = catId.split(",");
        int oneId = Integer.parseInt(split[0]);
        int twoId = Integer.parseInt(split[1]);
        int threeId = Integer.parseInt(split[2]);
        Date date = new Date();
        String str = date.getTime() / 1000 + "";
        int time = Integer.parseInt(str);
        goodsMapper.addGoods(name,oneId,twoId,threeId,price,number,weight,introduce,cateId,time,time);
    }

    @Override
    public int getGoodsIdByName(String name) {
        Goods goods = goodsMapper.getGoodsIdByName(name);
        return goods.getId();
    }

    @Override
    public void addgoodsAttrValue(int goodsId, int attrId, String attrValue) {
        goodsMapper.addgoodsAttrValue(goodsId,attrId,attrValue);
    }

    @Override
    public Map getGoodsById(int id) {
        Goods goods = goodsMapper.getGoodsById(id);
        HashMap<String,Object> map = new HashMap<>();
        map.put("goods_id",goods.getId());
        map.put("goods_name",goods.getName());
        map.put("goods_price",goods.getPrice());
        map.put("goods_weight",goods.getWeight());
        map.put("goods_number",goods.getNumber());
        map.put("goods_introduce",goods.getIntroduce());
        return map;
    }

    @Override
    public void updateGoodsById(int goodsId,String goodsName,BigDecimal goodsPrice,int goodsWeight,int goodsNumber,String introduce) {
        Date date = new Date();
        String str = date.getTime() / 1000 + "";
        int time = Integer.parseInt(str);
        goodsMapper.updateGoodsById(goodsId,goodsName,goodsPrice,goodsWeight,goodsNumber,introduce,time);
    }


}
