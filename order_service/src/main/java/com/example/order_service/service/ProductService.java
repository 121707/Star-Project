package user_shop.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import user_shop.demo.entity.NewProductEntity;
import user_shop.demo.entity.PageEntity;
import user_shop.demo.entity.Product;
import user_shop.demo.entity.ProductCondition;

import java.io.IOException;

public interface ProductService extends IService<Product> {

    //根据品牌、名字来匹配
    public Page<Product> pageByKey(PageEntity<ProductCondition> p);

    //根据品牌、名字来匹配秒杀
    public Page<Product> pageSecByKey(PageEntity<ProductCondition> p);

    //库存-1
    public boolean subStockByKey(Integer num , Integer productId);

    //库存-1
    public boolean subRedisStockByKey(Integer num , Integer productId);



    //新商品
    public boolean addNewProduct(NewProductEntity np , MultipartFile file , Integer productId) throws IOException;


}
