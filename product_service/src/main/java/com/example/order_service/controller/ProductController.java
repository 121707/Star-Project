package com.example.order_service.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.order_service.entity.PageEntity;
import com.example.order_service.entity.Product;
import com.example.order_service.entity.ProductCondition;
import com.example.order_service.service.Imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/productService")
public class ProductController {
    @Autowired
    ProductServiceImp pImp;

    @PostMapping("/productList")
    public Map<String, Object> productList(@RequestBody PageEntity<ProductCondition> pe){
        Page<Product> p = pImp.pageByKey(pe);
        Map<String , Object> ans = new HashMap<>();
        System.out.println(124);
        ans.put("list",p.getRecords());
        ans.put("total" , p.getTotal());
        return ans;
    }

    @PostMapping("/secKillList")
    public Map<String, Object> secKillList(@RequestBody PageEntity<ProductCondition> pe){
        Page<Product> p = pImp.pageSecByKey(pe);
        Map<String , Object> ans = new HashMap<>();
        ans.put("list",p.getRecords());
        ans.put("total" , p.getTotal());

        return ans;
    }

    
}
