package com.example.user_shop.demo.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.user_shop.demo.Dao.OrderDao;
import com.example.user_shop.demo.entity.Orders;
import com.example.user_shop.demo.entity.Product;
import com.example.user_shop.demo.service.OrderService;

import javax.annotation.Resource;

@Service
public class OrderServiceImp extends ServiceImpl<OrderDao , Orders> implements OrderService {
    @Resource
    OrderDao orderDao;

    @Autowired
    ProductServiceImp productServiceImp;

    //分别获取买卖次数
    @Override
    public Integer[] getOrderNumById(Integer productId) {
        QueryWrapper<Orders> q = new QueryWrapper<>();
        Integer[] ans = new Integer[2];

        q.eq("buyer_id",productId);
        ans[0] = this.count(q);
        q.clear();

        q.eq("seller_id",productId);
        ans[1] = this.count(q);

        return ans;
    }

    //获取最近30天收入
    @Override
    public Integer inputMonth(Integer userId) {
        return orderDao.inputMonth(userId);
    }

    @Override
    public boolean addNewOrder(Integer productId , Integer buyerId) {
        Orders orders = new Orders();
        String foundTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        Product p = productServiceImp.getById(productId);

        orders.setBuyerId(buyerId).setSellerId(p.getUserId()).
                setPrice(p.getPrice()).setProductId(productId).setFoundTime(foundTime);
        return this.save(orders);
    }


}
