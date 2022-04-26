package user_shop.demo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import user_shop.demo.entity.PassEntity;
import user_shop.demo.entity.User;

import java.io.IOException;


public interface UserService extends IService<User> {
    public void updateAvatar(MultipartFile file , Integer userId) throws IOException;

    public boolean updateInfo(String name, String eamil,Integer userId);

    public boolean updatePass(PassEntity p , Integer userId);
}
