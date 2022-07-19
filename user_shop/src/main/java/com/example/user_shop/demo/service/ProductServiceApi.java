package com.example.user_shop.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.user_shop.demo.config.MultiPartSupportConfiguration;
import com.example.user_shop.demo.entity.NewProductEntity;
import com.example.user_shop.demo.entity.PageEntity;
import com.example.user_shop.demo.entity.Product;
import com.example.user_shop.demo.entity.ProductCondition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "product-service",path = "/productService")
@Component
public interface ProductServiceApi {

    @PostMapping(value = "/addNewProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean uploadNewProduct(@RequestParam("npJson")String npJson, @RequestPart("file") MultipartFile file
            , @RequestParam("userId") Integer userId);

    @PostMapping(value = "/productList")
    public Page<Product> productList(@RequestBody PageEntity<ProductCondition> pe);
}
