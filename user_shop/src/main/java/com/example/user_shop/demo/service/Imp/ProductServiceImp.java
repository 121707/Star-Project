package user_shop.demo.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import user_shop.demo.Dao.ProductDao;
import user_shop.demo.entity.NewProductEntity;
import user_shop.demo.entity.PageEntity;
import user_shop.demo.entity.Product;
import user_shop.demo.entity.ProductCondition;
import user_shop.demo.service.ProductService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class ProductServiceImp extends ServiceImpl<ProductDao , Product> implements ProductService {

//    @Resource
//    private RedisTemplate<String , Object> redisTemplate;

    @Autowired
    private ProductDao dao;

    @Value(("${product.pictureBaseSrc}"))
    private String pictureBaseSrc;

    @Value("${product.pagesize}")
    private Integer pageSize;

    @Value("${product.pictureSuffix}")
    private String pictureSuffix;
    //通过关键字和价格进行查询,过审、开售的商品
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

    //通过关键字和价格进行查询,过审、即将开售的商品
    //秒杀商品开售后继续停留20分钟
    @Override
    public Page<Product> pageSecByKey(PageEntity<ProductCondition> p) {
        Page<Product> page = new Page<>(p.getCurpage() , pageSize);
        ProductCondition pc = p.getEntity();

        String key = pc.getKey();
        Double minp = pc.getMinPrice() == null ? 0.0 : pc.getMinPrice()
                , maxp = pc.getMaxPrice() == null ? Double.MAX_VALUE : pc.getMaxPrice();
        QueryWrapper<Product> q = new QueryWrapper<>();

        if(pc.getKey().length() > 0){
            q.and(Wrapper -> Wrapper.like("name" , key).or().like("brand" , key));
        }
        q.ge("price" , minp).le("price" , maxp)
                .eq("publish_status" , 1)
                .eq("audit_status" , 2)
                .between("TIMESTAMPDIFF(MINUTE , now() , sell_time)",-20,60);
        return this.page(page , q);
    }

    @Override
    public boolean subStockByKey(Integer num, Integer productId) {
        Product p = this.getById(productId);
        if(p.getStock() > 0)return dao.subStock(num , productId);
        return false;
    }

    //redis库存-1专用方法，先看redis再看mysql
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
    public boolean addNewProduct(NewProductEntity np, MultipartFile file , Integer userId) throws IOException {
        Product p = new Product();
        String applyTime = new DateTime().toString("yyyy-MM-dd hh-mm-ss");
        int maxId = this.count();

        String pictureName = "/" + String.valueOf(maxId) + UUID.randomUUID() + pictureSuffix;
        String desUrl = pictureBaseSrc + pictureName;
        file.transferTo(new File(desUrl));

        p.setApplyTime(applyTime).setBrand(np.getBrand()).setCategory(np.getCategory()).setName(np.getName())
                .setUserId(userId).setStock(np.getStock()).setPrice(np.getPrice()).setPictureUrl(pictureName).setSellTime(np.getSellTime());

        return this.save(p);

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
