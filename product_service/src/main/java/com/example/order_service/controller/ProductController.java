package com.example.order_service.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.order_service.entity.NewProductEntity;
import com.example.order_service.entity.PageEntity;
import com.example.order_service.entity.Product;
import com.example.order_service.entity.ProductCondition;
import com.example.order_service.service.Imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/productService")
public class ProductController {
    @Autowired
    ProductServiceImp pImp;

    @Autowired
    RedisTemplate redisTemplateDB1;

    @PostMapping("/productList")
    public Map<String, Object> productList(@RequestBody PageEntity<ProductCondition> pe){
        Page<Product> p = pImp.pageByKey(pe);
        Map<String , Object> ans = new HashMap<>();
        ans.put("list",p.getRecords());
        ans.put("total" , p.getTotal());
        return ans;
    }

    @PostMapping(value = "/addNewProduct")
    public boolean addNewProduct(String npJson, @RequestPart("file")MultipartFile file, Integer userId) throws IOException {
        System.out.println(npJson);
        return pImp.addNewProduct(npJson, file, userId);
    }

    @PostMapping(value = "/secKillList")
    public Map<String, Object> getSecKillList(@RequestBody PageEntity<ProductCondition> p){
        Page<Product> page = pImp.pageSecByKey(p);
        Map<String , Object> ans = new HashMap<>();
        ans.put("list",page.getRecords());
        ans.put("total" , page.getTotal());

        return ans;
    }

    //判断某商品是不是秒杀商品
    @PostMapping("/isSecProduct")
    public boolean isSecProduct(Integer productId){
        return redisTemplateDB1.hasKey(productId);
    }



}
