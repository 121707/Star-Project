package com.example.user_service.controller;

import com.example.user_service.entity.User;
import com.example.user_service.service.Imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class controller {

    @Autowired
    UserServiceImp userServiceImp;

    //通用ID获取用户
    @PostMapping("/getUserById")
    @ResponseBody
    public User getUserById(Integer userId){
        return userServiceImp.getById(userId);
    }


}
