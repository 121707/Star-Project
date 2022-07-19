package com.example.order_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.order_service.entity.NewProductEntity;
import com.example.order_service.entity.PageEntity;
import com.example.order_service.entity.Product;
import com.example.order_service.entity.ProductCondition;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService extends IService<Product> {

    //根据品牌、名字来匹配
    public Page<Product> pageByKey(PageEntity<ProductCondition> p);

    //根据品牌、名字来匹配秒杀
    public Page<Product> pageSecByKey(PageEntity<ProductCondition> p);

    //库存-1
    public boolean subStockByKey(Integer num , Integer productId);

//    //库存-1
//    public boolean subRedisStockByKey(Integer num , Integer productId);

    //新商品
    public boolean addNewProduct(String npJson , MultipartFile file , Integer productId) throws IOException;

    //获取Sql可以被展示的秒杀商品
    public List<Product> getPreSecKillList();


}
