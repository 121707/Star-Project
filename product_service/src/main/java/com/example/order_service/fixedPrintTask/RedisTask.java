package com.example.order_service.fixedPrintTask;

import com.example.order_service.entity.Product;
import com.example.order_service.service.Imp.ProductServiceImp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTask {
    @Autowired
    RedisTemplate redisTemplateDB1;

    @Autowired
    ProductServiceImp productServiceImp;

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void addSecProduct() throws ParseException {
        List<Product> secKillProduct = productServiceImp.getPreSecKillList();
        for(Product p : secKillProduct){
            if(!redisTemplateDB1.hasKey(String.valueOf(p.getProductId()))){
                redisTemplateDB1.opsForValue().set(String.valueOf(p.getProductId()), "", 40 * 60, TimeUnit.SECONDS);
            }
        }
    }
}
