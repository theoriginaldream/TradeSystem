package com.example.service;

import com.example.pojo.Order;

import java.util.List;

public interface OrderService {
    int addOrder(Order order);

    int deleteOrder(int orderid);

    int updateOrder(Order order);

    Order queryOrderById(int orderid);

    List<Order> queryOrderBySeller(String seller);

    List<Order> queryOrderByBuyer(String buyer);
}
