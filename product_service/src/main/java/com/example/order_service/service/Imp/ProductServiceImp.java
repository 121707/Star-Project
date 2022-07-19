package com.example.order_service.service.Imp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order_service.Dao.ProductDao;
import com.example.order_service.entity.NewProductEntity;
import com.example.order_service.entity.PageEntity;
import com.example.order_service.entity.Product;
import com.example.order_service.entity.ProductCondition;
import com.example.order_service.service.ProductService;
import com.example.order_service.util.OSSUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
public class ProductServiceImp extends ServiceImpl<ProductDao, Product> implements ProductService {

    @Autowired
    private ProductDao dao;

    @Value(("${product.pictureBaseSrc}"))
    private String pictureBaseSrc;

    @Value("${product.pagesize}")
    private Integer pageSize;

    @Value("${product.pictureSuffix}")
    private String pictureSuffix;

    @Autowired
    private OSSUtil ossUtil;

    @Autowired
    private RedisTemplate redisTemplateDB1;

    //通过关键字和价格进行查询,过审、开售的商品
    @Override
    public Page<Product> pageSecByKey(PageEntity<ProductCondition> p) {
        Page<Product> page = new Page<>(p.getCurpage() , pageSize);
        ProductCondition pc = p.getEntity();
        String key = pc.getKey();
        Double minp = pc.getMinPrice() == null ? 0.0 : pc.getMinPrice()
                , maxp = pc.getMaxPrice() == null ? Double.MAX_VALUE : pc.getMaxPrice();

        //DB1中得到的所有key都是可以被展示的商品的productId
        List<String> list = new ArrayList<>(redisTemplateDB1.keys("*"));

        QueryWrapper<Product> q = new QueryWrapper<>();

        q.in("product_id",list);
        if(pc.getKey().length() > 0) {
            q.and(Wrapper -> Wrapper.like("name", key).or().like("brand", key));
        }
        q.ge("price" , minp).le("price" , maxp);

        return this.page(page , q);
    }

    //通过关键字和价格进行查询,过审、即将开售的商品
    @Override
    public Page<Product> pageByKey(PageEntity<ProductCondition> p) {
        Page<Product> page = new Page<>(p.getCurpage() , pageSize);
        ProductCondition pc = p.getEntity();
        String key = pc.getKey();
        Double minp = pc.getMinPrice() == null ? 0.0 : pc.getMinPrice()
                , maxp = pc.getMaxPrice() == null ? Double.MAX_VALUE : pc.getMaxPrice();
        QueryWrapper<Product> q = new QueryWrapper<>();

        if(pc.getKey().length() > 0){
            q.and(Wrapper -> Wrapper.like("name" , key).or().like("brand" , key));
        }
        q.ge("price" , minp).le("price" , maxp);
        q.eq("publish_status" , 1);
        q.eq("audit_status" , 2);
        q.le("sell_time",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));

        return this.page(page , q);
    }

    @Override
    public boolean subStockByKey(Integer num, Integer productId) {
        Product p = this.getById(productId);
        if(p.getStock() > 0)return dao.subStock(num , productId);
        return false;
    }

//    //redis库存-1专用方法，先看redis再看mysql
//    @Override
//    public boolean subRedisStockByKey(Integer num, Integer productId) {
//        UpdateWrapper<Product> u = new UpdateWrapper<>();
//        u.eq("product_id" , productId);
//        u.set("stock",0);
//
//        String key = String.valueOf(productId);
//        if(redisTemplate.hasKey(key)){
//            Integer remain_stock = (Integer) redisTemplate.opsForValue().get(key);
//            if(remain_stock > 0)redisTemplate.opsForValue().decrement(key);
//            else return this.update(u);
//        }else return subStockByKey(num,productId);
//        return true;
//    }

    //新商品
    @Override
    public boolean addNewProduct(String npJson, MultipartFile file , Integer userId) throws IOException {
        Product p = new Product();

        NewProductEntity np = JSONObject.parseObject(npJson, NewProductEntity.class);
        System.out.println(np);
        String applyTime = new DateTime().toString("yyyy-MM-dd hh-mm-ss");
        int maxId = this.count();

        String pictureName =String.valueOf(maxId) + UUID.randomUUID() + pictureSuffix;
        String pictureUrl = "http://" + ossUtil.uploadNewPPictiure(file, pictureName);

        p.setApplyTime(applyTime).setBrand(np.getBrand()).setCategory(np.getCategory()).setName(np.getName())
                .setUserId(userId).setStock(np.getStock()).setPrice(np.getPrice()).setPictureUrl(pictureUrl).setSellTime(np.getSellTime());


        return this.save(p);

    }

    @Override
    public List<Product> getPreSecKillList() {
        QueryWrapper<Product> p = new QueryWrapper<>();
        p.between("timestampdiff(minute , now() , sell_time)",0,20);
        p.eq("publish_status" , 1);
        p.eq("audit_status" , 2);

        List<Product> ans =  this.list(p);
        return ans;
    }



//    //判断商品上架时间，对秒杀商品的库存提前保存redis
//    public void addStockRedis(){
//        QueryWrapper<Product> p = new QueryWrapper<>();
//        UpdateWrapper<Product> u = new UpdateWrapper<>();
//        //把过期但没卖完的商品从redis删除，并更新数据库stock
//        p.lt("timestampdiff(minute , now() , sell_time)" , -20);
//        for(Product i : this.list(p)){
//            String key = String.valueOf(i.getProductId());
//            //判断符合条件的product是否存在id于redis
//            if(redisTemplate.hasKey(key)){
//                Integer remain_stock = (Integer) redisTemplate.opsForValue().get(key);
//                //如果存在更新库存
//                u.eq("product_id",key);
//                u.set("stock",remain_stock);
//                //redis删除库存信息
//                redisTemplate.delete(key);
//            }
//        }
//        p.clear();
//
//        p.between("timestampdiff(minute , now() , sell_time)" , 0 , 60);
//        for(Product i : this.list(p)){
//            String key = String.valueOf(i.getProductId());
//            if(!redisTemplate.hasKey(key))redisTemplate.opsForValue().set(key , i.getStock());
//        }
//
//        System.out.println(redisTemplate.keys("*"));
//    }
}
