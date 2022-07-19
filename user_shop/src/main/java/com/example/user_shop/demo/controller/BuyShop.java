package com.example.user_shop.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.user_shop.demo.entity.PageEntity;
import com.example.user_shop.demo.entity.Product;
import com.example.user_shop.demo.entity.ProductCondition;
import com.example.user_shop.demo.service.Imp.ProductServiceImp;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BuyShop {
    @Autowired
    private ProductServiceImp imp;

    @ResponseBody
    @PostMapping("/shop/productList")
    public Map<String, Object> productList(@RequestBody PageEntity<ProductCondition> pe){
        Page<Product> p = imp.pageByKey(pe);
        Map<String , Object> ans = new HashMap<>();
        ans.put("list",p.getRecords());
        ans.put("total" , p.getTotal());
        return ans;
    }

//    @ResponseBody
//    @PostMapping("/shop/secKillList")
//    public Map<String, Object> secKillList(@RequestBody PageEntity<ProductCondition> pe){
//        Page<Product> p = imp.pageSecByKey(pe);
//        Map<String , Object> ans = new HashMap<>();
//        ans.put("list",p.getRecords());
//        ans.put("total" , p.getTotal());
//
//        return ans;
//    }


}
