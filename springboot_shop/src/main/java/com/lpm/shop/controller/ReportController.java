package com.lpm.shop.controller;

import com.lpm.shop.server.imp.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReportController {

    @Autowired
    ReportServiceImpl reportService;

    @GetMapping("/reports")
    @ResponseBody
    public Map getData(){
        List date = reportService.getDate();
        List areaUserCount = reportService.getAreaUserCount();
        HashMap<String,Object> metaMap = new HashMap<>();
        HashMap<String,Object> map = new HashMap<>();
        metaMap.put("status",200);
        metaMap.put("msg","获取数据成功");
        map.put("meta",metaMap);
        map.put("x",date);
        map.put("data",areaUserCount);
        return map;
    }
}
