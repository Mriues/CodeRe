package com.lpm.shop.server;

import java.util.Map;

public interface OrderService {

    Map getOrderList(String query,int pagenum,int pagesize,int userId,String payStatus,String isSend,String title,String company,String content,String addr);
}
