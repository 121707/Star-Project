package user_shop.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import user_shop.demo.service.Imp.ProductServiceImp;

import java.text.ParseException;


//@Component
public class FixedPrintTask {
    @Autowired
    ProductServiceImp productServiceImp;

    @Scheduled(fixedDelay = 15 * 60 * 1000 )
    public void addRedisStock() throws ParseException {
//        productServiceImp.addStockRedis();
    }
}
