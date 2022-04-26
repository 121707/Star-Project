package test.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

@TableName
@Data
public class user {
    @TableId("user_id")
    int userId;

    @TableField("user_name")
    String userName;
    @TableField("user_password")
    String userPassword;
    String phone;
    @TableField("ic_num")
    String icNum;
    @TableField("user_type")
    String userType;
    @TableField("credit_rating")
    int creditRating;
    @TableField("register_time")
    Date registerTime;
    @TableField("last_login_time")
    java.util.Date lastLoginTime;
    int balance;
}
