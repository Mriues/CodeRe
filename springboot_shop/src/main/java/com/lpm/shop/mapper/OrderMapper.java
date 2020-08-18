package com.lpm.shop.mapper;

import com.lpm.shop.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    List<Order> getOrderList(@Param("query") String query, int pagenum, int pagesize, int userId, String payStatus, String isSend, String title, String company, String content, String addr);
}
