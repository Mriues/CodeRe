package com.lpm.shop.server.imp;

import com.lpm.shop.entity.Order;
import com.lpm.shop.mapper.OrderMapper;
import com.lpm.shop.server.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public Map getOrderList(String query,int pagenum,int pagesize,int userId,String payStatus,String isSend,String title,String company,String content,String addr) {
        List<Order> orderList = orderMapper.getOrderList(query,pagenum,pagesize,userId,payStatus,isSend,title,company,content,addr);
        List list = new ArrayList();
        HashMap<String,Object> data = new HashMap<>();
        for (Order order : orderList) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("order_id",order.getOrderId());
            map.put("user_id",order.getUserId());
            map.put("order_number",order.getOrderNumber());
            map.put("order_price",order.getOrderPrice());
            map.put("order_pay",order.getOrderPay());
            map.put("is_send",order.getIsSend());
            map.put("trade_no",order.getTradeNo());
            map.put("order_fapiao_title",order.getOrderFapiaoTitle());
            map.put("order_fapiao_company",order.getOrderFapiaoCompany());
            map.put("order_fapiao_content",order.getOrderFapiaoContent());
            map.put("consignee_addr",order.getAddr());
            map.put("pay_status",order.getPayStatus());
            map.put("create_time",order.getCreateTime());
            map.put("update_time",order.getUpdateTime());
            list.add(map);
        }
        //分页数据控制
        List resultList = new ArrayList();
        if (pagesize > list.size()){
            for (int i = 0;i < list.size();i++){
                resultList.add(list.get(i));
            }
        }else if (pagesize < list.size()){
            for (int i = 0;i < pagesize;i++){
                if (pagesize*(pagenum-1) + i > list.size()-1){
                    break;
                }
                resultList.add(list.get(pagesize*(pagenum-1) + i));
            }
        }
        data.put("pagenum",pagenum);
        data.put("total",list.size());
        data.put("goods",resultList);
        return data;
    }
}
