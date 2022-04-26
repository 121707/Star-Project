package management_worker.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import management_worker.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao extends BaseMapper<User> {


   //id删除
    @Delete("delete from user where user_id = #{user_id}")
    public int removeById(int user_id);

    //数据更新
    @Update("update user set user_name = #{userName} , email = #{email} ," +
            "phone = #{phone} , user_type = #{userType} , user_status = #{userStatus} , " +
            "credit_rating = #{userCredit} where user_id = #{userId}")
    public int update(@Param("userId") int userId , @Param("userName") String userName , @Param("email")String email ,
                      @Param("phone") String phone , @Param("userType") String userType
                     ,@Param("userStatus") String userStatus ,@Param("userCredit") int userCredit);

    @Update("update user set avatar_url = #{avatarUrl} where user_id = #{userId}")
    public boolean updateavatar(@Param("avatarUrl")String avatarUrl , @Param("userId")Integer userId);

    @Select("select avatar_url from user where user_id = ${userId}")
    public String getAvatarUrlById(@Param("userId") Integer userId);


}
