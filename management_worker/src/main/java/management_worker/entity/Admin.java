package management_worker.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("admin")
public class Admin {
    @TableId
    Integer adminId;

    String adminPassword;
    String adminType;
    String lastLoginTime;
    String registerTime;
    String adminStatus;
    Integer registerId;
    String phone;
    Integer employeeId;

    public void setLastLoginTime(String lastLoginTime){
        this.lastLoginTime = lastLoginTime.substring(0,19);
    }
}
