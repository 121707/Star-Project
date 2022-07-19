package user_shop.demo.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import user_shop.demo.entity.User;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
