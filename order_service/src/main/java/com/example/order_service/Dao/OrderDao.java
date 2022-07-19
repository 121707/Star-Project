package user_shop.demo.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import user_shop.demo.entity.Orders;
@Mapper
public interface OrderDao extends BaseMapper<Orders> {

    @Select("select sum(price) from orders where seller_id = #{sellId} and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= found_time")
    public Integer inputMonth(@Param("sellId")Integer sellId);
}
