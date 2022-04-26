package management_worker.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import management_worker.Dao.UserDao;
import management_worker.entity.User;
import management_worker.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImp extends ServiceImpl<UserDao, User> implements UserService {
    //头像目录前缀
    @Value("${user.avatarBaseSrc}")
    private String BaseSrc;
    //默认头像名称
    @Value("${user.avatarFaultName}")
    private String avatarFaultName;

    @Resource
    UserDao user_dao;

    @Override
    public String removeById(int userid) {
        if(user_dao.removeById(userid) == 1)return "删除成功!";
        return "删除失败,请重新操作";
    }

    @Override
    public String update(User user) {
         if(user_dao.update(user.getUserId(),user.getUserName(),user.getEmail(),
                 user.getPhone(),user.getUserType(),
                 user.getUserStatus(),user.getCreditRating()) == 1)return "修改成功";
         return "修改失败，请重新修改";
    }

    //返回条件查询条件
    @Override
    public Page<User> SelectBySome(Integer userID, String userName, String userType,
                                   String userStatus, String lastLoginTimeFirst, String registerTimeLasr,
                                   String registerTimeFirst, String lastLoginTimeLast , int curpage) {
        Page<User> page = new Page<>(curpage,10);
        QueryWrapper<User> qw = new QueryWrapper<>();
        if(userID != null)qw.eq("user_id",userID);
        if(!userName.equals(""))qw.like("user_name",userName);
        if(!lastLoginTimeFirst.equals("") && !lastLoginTimeLast.equals(""))qw.between("last_login_time",lastLoginTimeFirst,lastLoginTimeLast);
        if(!registerTimeFirst.equals("") && !registerTimeLasr.equals(""))qw.between("register_time",registerTimeFirst,registerTimeLasr);
        if(!userType.equals(""))qw.eq("user_type",userType);
        if(!userStatus.equals(""))qw.eq("user_status",userStatus);
        return this.page(page,qw);
    }

    @Override
    public String updateAvatar(String src, int userId) {
        boolean res = user_dao.updateavatar(src,userId);
        return res?"修改成功":"修改失败";
    }

    //替换头像资源
    @Override
    public void replaceFile(String oldUrl, String newUrl,MultipartFile avatar) throws IOException {
        File olddest = new File(BaseSrc  + oldUrl) ,
                newdest = new File(BaseSrc + newUrl);
        if(olddest.exists() && !oldUrl.equals(avatarFaultName))FileUtils.forceDelete(olddest);
        avatar.transferTo(newdest);

    }

    @Override
    public String getAvatarUrlById(Integer userId) {
        return user_dao.getAvatarUrlById(userId);
    }

    @Override
    public String adduser(User user) {
        Date register_time = new Date();
        user.setLastLoginTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(register_time));
        user.setRegisterTime(new SimpleDateFormat("yyyy-MM-dd").format(register_time));
        boolean res = this.save(user);
        return res?"添加用户"+user.getUserName()+"成功":"添加失败";
    }


}
