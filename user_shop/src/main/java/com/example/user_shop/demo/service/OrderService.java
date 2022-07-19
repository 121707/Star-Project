package com.example.user_shop.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.user_shop.demo.entity.Orders;

public interface OrderService extends IService<Orders> {
    //获取个人交易次数，0下标为买，1下标为卖
    public Integer[] getOrderNumById(Integer productId);

    //获取当月收入
    public Integer inputMonth(Integer userId);

    //新建订单
    public boolean addNewOrder(Integer productId , Integer buyerId);
}
