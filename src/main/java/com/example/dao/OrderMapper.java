package com.example.dao;

import com.example.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

    int addOrder(Order order);

    int deleteOrder(@Param("orderid") int orderid);

    int updateOrder(Order order);

    Order queryOrderById(@Param("orderid") int orderid);

    List<Order> queryOrderBySeller(@Param("seller") String seller);

    List<Order> queryOrderByBuyer(@Param("buyer") String buyer);



}
