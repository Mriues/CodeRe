package com.lpm.shop.controller;

import com.lpm.shop.server.imp.MenuServiceImpl;
import com.lpm.shop.server.imp.RightsServiceImpl;
import com.lpm.shop.util.MapResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RightsController {

    @Autowired
    MenuServiceImpl menuService;

    @Autowired
    RightsServiceImpl rightsService;

    @Autowired
    MapResultUtils mapResultUtils;

    @GetMapping("/rights/list")
    @ResponseBody
    public Map<String,Object> getList(){
        List rightsList = rightsService.getRightsList();
        HashMap<String, Object> map = mapResultUtils.resultMap(rightsList, 200, "获取权限列表成功");
        return map;
    }
}
