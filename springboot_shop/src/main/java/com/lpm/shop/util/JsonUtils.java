package com.lpm.shop.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.math.BigDecimal;

/**
 * json转换
 * @author Master
 */
@Component
public class JsonUtils {

    public String conversionStr(String json,String data){
        JSONObject object = JSONObject.parseObject(json);
        String str = object.get(data).toString();
        return str;
    }

    public int conversionInt(String json,String data){
        JSONObject object = JSONObject.parseObject(json);
        String str = object.get(data).toString();
        int i = Integer.parseInt(str);
        return i;
    }

    public BigDecimal conversionDec(String json,String data){
        JSONObject object = JSONObject.parseObject(json);
        String str = object.get(data).toString();
        BigDecimal bigDecimal = new BigDecimal(str);
        BigDecimal decimal = bigDecimal.setScale(2);
        return decimal;
    }

    public JSONArray conversionArray(String json, String data){
        JSONObject object = JSONObject.parseObject(json);
        JSONArray array = (JSONArray) object.get(data);
        return array;
    }
}
