package com.example.service;

import com.example.dao.OrderMapper;
import com.example.pojo.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public int addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    @Override
    public int deleteOrder(int orderid) {
        return orderMapper.deleteOrder(orderid);
    }

    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }

    @Override
    public Order queryOrderById(int orderid) {
        return orderMapper.queryOrderById(orderid);
    }

    @Override
    public List<Order> queryOrderBySeller(String seller) {
        return orderMapper.queryOrderBySeller(seller);
    }

    @Override
    public List<Order> queryOrderByBuyer(String buyer) {
        return orderMapper.queryOrderByBuyer(buyer);
    }
}
