package com.lpm.shop.controller;

import com.lpm.shop.server.imp.ReportServiceImpl;
import com.lpm.shop.util.MapResultUtils;
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

    @Autowired
    MapResultUtils mapResultUtils;

    @GetMapping("/reports")
    @ResponseBody
    public Map<String,Object> getData(){
        List date = reportService.getDate();
        List areaUserCount = reportService.getAreaUserCount();
        HashMap<String, Object> map = mapResultUtils.resultMap(areaUserCount, 200, "获取数据成功");
        map.put("x",date);
        return map;
    }
}
