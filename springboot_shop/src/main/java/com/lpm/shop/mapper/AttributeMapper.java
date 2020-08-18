package com.lpm.shop.mapper;

import com.lpm.shop.entity.Attribute;

import java.util.List;

public interface AttributeMapper {

    List<Attribute> getAttributeById(int id,String sel);

    Attribute getOnlyAttributeById(int cateId,int attrId,String attrSel,String attrVals);

    void addAttributeById(int cateId,String attrName,String attrSel,String attrVals);

    void updateAttributeById(int cateId,int attrId,String attrName,String attrVals);

    void deleteAttributeById(int cateId,int attrId);

    Attribute getCatIdByAttrId(int attrId);
}
