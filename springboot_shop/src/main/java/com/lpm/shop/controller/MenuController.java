package com.lpm.shop.controller;

import com.lpm.shop.server.imp.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author Master
 */
@Controller
public class MenuController {

    @Autowired
    MenuServiceImpl menuService;

    @RequestMapping("/menus")
    @ResponseBody
    public HashMap menulist(){
        HashMap map = new HashMap();
        List menus = menuService.buildMenuList();
        map.put("data",menus);
        return map;
    }
}
