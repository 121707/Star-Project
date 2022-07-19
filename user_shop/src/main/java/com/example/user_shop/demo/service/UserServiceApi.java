package com.example.user_shop.demo.service;

import com.example.user_shop.demo.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "user-service")
public interface UserServiceApi {

    //通用ID获取用户
    @PostMapping("/getUserById")
    public User getUserById(@RequestParam("userId") Integer userId);
}
