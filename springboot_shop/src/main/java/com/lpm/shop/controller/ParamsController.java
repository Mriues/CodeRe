package com.lpm.shop.controller;

import com.lpm.shop.server.imp.AttributeServiceImpl;
import com.lpm.shop.util.JsonUtils;
import com.lpm.shop.util.MapResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ParamsController {

    @Autowired
    JsonUtils jsonUtils;

    @Autowired
    AttributeServiceImpl attributeService;

    @Autowired
    MapResultUtils mapResultUtils;

    @GetMapping("/categories/{id}/attributes")
    @ResponseBody
    public Map<String,Object> getAttributeById(@PathVariable("id") int id, @RequestParam("sel") String sel){
        List attribute = attributeService.getAttributeById(id, sel);
        HashMap<String, Object> map = mapResultUtils.resultMap(attribute, 200, "获取成功");
        return map;
    }

    @PostMapping("/categories/{cateId}/attributes")
    @ResponseBody
    public Map<String,Object> addAttributeById(@PathVariable("cateId") int cateId,@RequestBody String data){
        String attrName = jsonUtils.conversionStr(data, "attr_name");
        String attrSel = jsonUtils.conversionStr(data, "attr_sel");
        String attrVals = jsonUtils.conversionStr(data, "attr_vals");
        attributeService.addAttributeById(cateId,attrName,attrSel,attrVals);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "添加参数成功");
        return map;
    }

    @GetMapping("/categories/{cateId}/attributes/{attrId}")
    @ResponseBody
    public Map<String,Object> getAttributeByid(@PathVariable("cateId") int cateId,
                                @PathVariable("attrId") int attrId,
                                @RequestParam("attr_sel") String attrSel,
                                @RequestParam(value = "attr_vals",required = false,defaultValue = "") String attrVals){
        Map attribute = attributeService.getOnlyAttributeById(cateId, attrId, attrSel, attrVals);
        HashMap<String, Object> map = mapResultUtils.resultMap(attribute, 200, "获取成功");
        return map;
    }

    @PutMapping("/categories/{cateId}/attributes/{attrId}")
    @ResponseBody
    public Map<String,Object> updateAttributeById(@PathVariable("cateId") int cateId,
                                         @PathVariable("attrId") int attrId,
                                         @RequestBody String data){
        String attrName = jsonUtils.conversionStr(data, "attr_name");
        String attrSel = jsonUtils.conversionStr(data, "attr_sel");
        String attrVals = jsonUtils.conversionStr(data, "attr_vals");
        attributeService.updateAttributeById(cateId,attrId,attrName,attrSel,attrVals);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "更新成功");
        return map;
    }

    @DeleteMapping("/categories/{cateId}/attributes/{attrId}")
    @ResponseBody
    public Map<String,Object> deleteAttributeById(@PathVariable("cateId") int cateId,@PathVariable("attrId") int attrId){
        attributeService.deleteAttributeById(cateId,attrId);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 204, "删除成功");
        return map;
    }

}
