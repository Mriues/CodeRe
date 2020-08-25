package com.lpm.shop.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MapResultUtils {

    public HashMap<String, Object> resultMap(Object data, int status, String msg){
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",status);
        metaMap.put("msg",msg);
        map.put("meta",metaMap);
        map.put("data",data);
        return map;
    }
}
