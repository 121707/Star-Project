package user_shop.demo.service.Imp;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import user_shop.demo.Dao.UserDao;
import user_shop.demo.entity.PassEntity;
import user_shop.demo.entity.User;
import user_shop.demo.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class UserServiceImp extends ServiceImpl<UserDao, User> implements UserService {

    @Value("${user.avatarBaseSrc}")
    private String baseSrc;

    @Value("${user.avatarFaultName}")
    private String defaultName;

    @Override
    public void updateAvatar(MultipartFile file , Integer userId) throws IOException {
        if(file == null)return;
        String oldUrl = baseSrc + this.getById(userId).getAvatarUrl();
        String newAvatarName = "/" + String.valueOf(userId) + UUID.randomUUID() + ".jpg";
        String newUrl = baseSrc + newAvatarName;

        UpdateWrapper<User> u = new UpdateWrapper<>();
        u.eq("user_id",userId);
        u.set("avatar_url" , newAvatarName);
        this.update(u);

        if(!newAvatarName.equals(defaultName))FileUtils.forceDelete(new File(oldUrl));
        file.transferTo(new File(newUrl));
    }

    @Override
    public boolean updateInfo(String name, String eamil, Integer userId) {
        UpdateWrapper<User> u = new UpdateWrapper<>();
        u.eq("user_id" , userId);
        u.set("user_name",name);
        u.set("email",eamil);

        return this.update(u);
    }

    @Override
    public boolean updatePass(PassEntity p, Integer userId) {
        UpdateWrapper<User> u = new UpdateWrapper<>();
        u.eq("user_id" , userId);
        u.set("user_password",new BCryptPasswordEncoder().encode(p.getNewpass1()));
        return this.update(u);
    }


}
