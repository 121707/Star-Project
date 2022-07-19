package user_shop.demo.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import user_shop.demo.entity.Product;
import user_shop.demo.entity.User;

@Mapper
public interface ProductDao extends BaseMapper<Product> {
    @Update("update product set stock = stock - #{num} where product_id = #{productId}")
    public boolean subStock(@Param("num") Integer num , @Param("productId") Integer productId);

}
