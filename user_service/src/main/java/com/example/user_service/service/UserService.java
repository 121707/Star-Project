package com.example.user_service.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.user_service.entity.PassEntity;
import com.example.user_service.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService extends IService<User> {
    public void updateAvatar(MultipartFile file , Integer userId) throws IOException;

    public boolean updateInfo(String name, String eamil,Integer userId);

    public boolean updatePass(PassEntity p , Integer userId);
}
