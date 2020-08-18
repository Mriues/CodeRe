package com.lpm.shop.server.imp;

import com.lpm.shop.entity.Attribute;
import com.lpm.shop.mapper.AttributeMapper;
import com.lpm.shop.server.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    AttributeMapper attributeMapper;

    @Override
    public List getAttributeById(int id, String sel) {
        List<Attribute> attribute = attributeMapper.getAttributeById(id, sel);
        List list = new ArrayList();
        for (Attribute attribute1 : attribute) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("attr_id",attribute1.getAttrId());
            map.put("attr_name",attribute1.getAttrName());
            map.put("cat_id",attribute1.getCatId());
            map.put("attr_sel",attribute1.getAttrSel());
            map.put("attr_write",attribute1.getAttrWrite());
            map.put("attr_vals",attribute1.getAttrVals());
            list.add(map);
        }
        return list;
    }

    @Override
    public Map getOnlyAttributeById(int cateId, int attrId, String attrSel, String attrVals) {
        Attribute attribute = attributeMapper.getOnlyAttributeById(cateId, attrId, attrSel, attrVals);
        HashMap<String,Object> map = new HashMap<>();
        map.put("attr_id",attribute.getAttrId());
        map.put("attr_name",attribute.getAttrName());
        map.put("cat_id",attribute.getCatId());
        map.put("attr_sel",attribute.getAttrSel());
        map.put("attr_write",attribute.getAttrWrite());
        map.put("attr_vals",attribute.getAttrVals());
        return map;
    }

    @Override
    public void addAttributeById(int cateId, String attrName, String attrSel, String attrVals) {
        if (attrVals == null){
            attributeMapper.addAttributeById(cateId,attrName,attrSel,"");
        }else {
            attributeMapper.addAttributeById(cateId,attrName,attrSel,attrVals);
        }
    }

    @Override
    public void updateAttributeById(int cateId, int attrId, String attrName,String attrSel,String attrVals) {
        if (attrVals == null){
            attributeMapper.updateAttributeById(cateId,attrId,attrName,"");
        }else {
            attributeMapper.updateAttributeById(cateId,attrId,attrName,attrVals);
        }
    }

    @Override
    public void deleteAttributeById(int cateId, int attrId) {
        attributeMapper.deleteAttributeById(cateId,attrId);
    }

    @Override
    public int getCatIdByAttrId(int attrId) {
        Attribute attribute = attributeMapper.getCatIdByAttrId(attrId);
        return attribute.getCatId();
    }

}
