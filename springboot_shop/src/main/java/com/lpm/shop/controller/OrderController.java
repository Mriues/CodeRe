package com.lpm.shop.controller;

import com.lpm.shop.server.imp.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @GetMapping("/orders")
    @ResponseBody
    public Map getOrderList(@RequestParam(value = "query",required = false,defaultValue = "") String query,
                            @RequestParam("pagenum") int pagenum,
                            @RequestParam("pagesize") int pagesize,
                            @RequestParam(value = "user_id",required = false,defaultValue = "0") Integer userId,
                            @RequestParam(value = "pay_status",required = false) String payStatus,
                            @RequestParam(value = "is_send",required = false) String isSend,
                            @RequestParam(value = "order_fapiao_title",required = false) String title,
                            @RequestParam(value = "order_fapiao_company",required = false) String company,
                            @RequestParam(value = "order_fapiao_content",required = false) String content,
                            @RequestParam(value = "consignee_addr",required = false) String addr){
        Map orderMap = orderService.getOrderList(query,pagenum,pagesize,userId,payStatus,isSend,title,company,content,addr);
        HashMap<String,Object> metaMap = new HashMap<>();
        HashMap<String,Object> map = new HashMap<>();
        metaMap.put("status",200);
        metaMap.put("msg","获取订单信息成功");
        map.put("meta",metaMap);
        map.put("data",orderMap);
        return map;
    }
}
