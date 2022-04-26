package management_worker.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;



@TableName("user")
@Data
public class User {
    @TableId(value = "user_id" , type = IdType.AUTO)
    private Integer userId;

    private String userName;
    private String userPassword;
    private String phone;
    private String userType;
    private Integer creditRating;
    private String registerTime;
    private String lastLoginTime;
    private Integer balance;
    private String email;
    private String userStatus;
    private String avatarUrl;

    public void setLastLoginTime(String lastLoginTime){
        if(lastLoginTime.length() > 19)this.lastLoginTime = lastLoginTime.substring(0,19);
        else this.lastLoginTime = lastLoginTime;

    }
}
