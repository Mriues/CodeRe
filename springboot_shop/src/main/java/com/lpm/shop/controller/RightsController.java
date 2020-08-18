package com.lpm.shop.controller;

import com.lpm.shop.server.imp.MenuServiceImpl;
import com.lpm.shop.server.imp.RightsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class RightsController {

    @Autowired
    MenuServiceImpl menuService;

    @Autowired
    RightsServiceImpl rightsService;

    @GetMapping("/rights/list")
    @ResponseBody
    public HashMap<String,Object> getList(){
        HashMap<String,Object> map = new HashMap();
        HashMap<String,Object> metaMap = new HashMap();
        List rightsList = rightsService.getRightsList();
        map.put("data",rightsList);
        metaMap.put("msg","获取权限列表成功");
        metaMap.put("status",200);
        map.put("meta",metaMap);
        return map;
    }
}
