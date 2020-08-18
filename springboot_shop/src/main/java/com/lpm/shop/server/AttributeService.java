package com.lpm.shop.server;


import java.util.List;
import java.util.Map;

public interface AttributeService {

    List getAttributeById(int id,String sel);

    Map getOnlyAttributeById(int cateId, int attrId, String attrSel, String attrVals);

    void addAttributeById(int cateId,String attrName,String attrSel,String attrVals);

    void updateAttributeById(int cateId,int attrId,String attrName,String attrSel,String attrVals);

    void deleteAttributeById(int cateId,int attrId);

    int getCatIdByAttrId(int attrId);

}
