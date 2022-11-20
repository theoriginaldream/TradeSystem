package com.example.controller;

import com.example.pojo.Item;
import com.example.pojo.Order;
import com.example.pojo.OrderItem;
import com.example.service.ItemService;
import com.example.service.OrderService;
import com.example.service.RequireItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
@Api
public class OrderController {

    @Autowired
    @Qualifier("itemServiceImpl")
    private ItemService itemService;

    @Autowired
    @Qualifier("requireItemServiceImpl")
    private RequireItemService requireItemService;

    @Autowired
    @Qualifier("orderServiceImpl")
    private OrderService orderService;

    @RequestMapping(value = "/add/order",method = RequestMethod.POST)
    @ResponseBody
    public String addOrder(@RequestParam("itemid") int itemid, HttpSession session){
        Order order = new Order();
        order.setItemid(itemid);
        Item item = itemService.queryItemById(itemid);
        order.setSeller(item.getHost());
        String admin = (String) session.getAttribute("admin");
        order.setBuyer(admin);
        order.setDatetime(new Date());
        order.setStatus("未完成");
        orderService.addOrder(order);

        return "success";
    }

    @RequestMapping(value = "/delete/{orderid}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteOrder(@PathVariable("orderid") int orderid,HttpSession session){
        String admin = (String) session.getAttribute("admin");
        Order order = orderService.queryOrderById(orderid);
        if (admin!=null && (admin.equals(order.getBuyer()) || admin.equals(order.getSeller()))){
            orderService.deleteOrder(orderid);
            return "success";
        }else {
            return "无权限删除";
        }
    }

    @RequestMapping(value = "/update/status/{orderid}",method = RequestMethod.POST)
    @ResponseBody
    public String updateStatus(@PathVariable("orderid") int orderid,HttpSession session){
        String admin = (String) session.getAttribute("admin");
        Order order = orderService.queryOrderById(orderid);

        if (admin!=null && "未完成".equals(order.getStatus()) && admin.equals(order.getBuyer())){
            order.setStatus("已完成");
            return "success";
        }else {
            return "无权限更改";
        }
    }

    @RequestMapping(value = "/mine",method = RequestMethod.GET)
    @ResponseBody
    public List<OrderItem> queryMyOrder(HttpSession session){
        String admin = (String) session.getAttribute("admin");
        List<Order> ordersSeller = orderService.queryOrderBySeller(admin);
        List<Order> ordersBuyer = orderService.queryOrderByBuyer(admin);

        List<OrderItem> orders = new ArrayList<>();

        for (Order order : ordersSeller) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderid(order.getOrderid());
            orderItem.setItemid(order.getItemid());
            orderItem.setBuyer(order.getBuyer());
            orderItem.setSeller(order.getSeller());
            orderItem.setStatus(order.getStatus());
            orderItem.setDatetime(order.getDatetime());
            Item item = itemService.queryItemById(order.getItemid());
            orderItem.setItemname(item.getItemname());
            orderItem.setItempicture(item.getItempicture());
            orderItem.setPrice(item.getPrice());
            orders.add(orderItem);
        }

        for (Order order : ordersBuyer) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderid(order.getOrderid());
            orderItem.setItemid(order.getItemid());
            orderItem.setBuyer(order.getBuyer());
            orderItem.setSeller(order.getSeller());
            orderItem.setStatus(order.getStatus());
            orderItem.setDatetime(order.getDatetime());
            Item item = itemService.queryItemById(order.getItemid());
            orderItem.setItemname(item.getItemname());
            orderItem.setItempicture(item.getItempicture());
            orderItem.setPrice(item.getPrice());
            orders.add(orderItem);
        }

        return orders;
    }

    @RequestMapping(value = "/order/{orderid}",method = RequestMethod.GET)
    @ResponseBody
    public OrderItem queryOrderById(@PathVariable("orderid") int orderid,HttpSession session){
        String admin = (String) session.getAttribute("admin");
        Order order = orderService.queryOrderById(orderid);
        OrderItem orderItem = new OrderItem();
        if (admin!=null && (admin.equals(order.getBuyer()) || admin.equals(order.getSeller()))){
            orderItem.setOrderid(order.getOrderid());
            orderItem.setItemid(order.getItemid());
            orderItem.setBuyer(order.getBuyer());
            orderItem.setSeller(order.getSeller());
            orderItem.setStatus(order.getStatus());
            orderItem.setDatetime(order.getDatetime());
            Item item = itemService.queryItemById(order.getItemid());
            orderItem.setItemname(item.getItemname());
            orderItem.setItempicture(item.getItempicture());
            orderItem.setPrice(item.getPrice());
            return orderItem;
        }else {
            return orderItem;
        }
    }

}
