package com.example.order_service.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.order_service.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductDao extends BaseMapper<Product> {
    @Update("update product set stock = stock - #{num} where product_id = #{productId}")
    public boolean subStock(@Param("num") Integer num , @Param("productId") Integer productId);

}
