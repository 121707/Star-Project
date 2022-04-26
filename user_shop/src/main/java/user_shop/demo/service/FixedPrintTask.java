package user_shop.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import user_shop.demo.service.Imp.ProductServiceImp;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class FixedPrintTask {
    @Autowired
    ProductServiceImp productServiceImp;

    @Scheduled(fixedDelay = 15 * 60 * 1000 )
    public void addRedisStock() throws ParseException {
        productServiceImp.addStockRedis();
    }
}
