package user_shop.demo.controller;

import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import user_shop.demo.entity.NewProductEntity;
import user_shop.demo.entity.PassEntity;
import user_shop.demo.entity.UserInfo;
import user_shop.demo.service.Imp.ProductServiceImp;
import user_shop.demo.service.Imp.UserServiceImp;

import java.io.IOException;
import java.security.Principal;

@Controller
public class User {
    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("/user/updateUserInfo")
    public String m1(@RequestParam("name")String name , @RequestParam("email")String email ,
                     @RequestParam(value = "avatar" , required = false)MultipartFile file, Principal principal , Model model) throws IOException {
        Integer userId = Integer.valueOf(principal.getName());
        userServiceImp.updateAvatar(file , userId);
        userServiceImp.updateInfo(name,email,userId);
        model.addAttribute(userServiceImp.getById(userId));
        return "userpage::userInfo";
    }


    //改密码
    @PostMapping("/user/updatePassword")
    public String m2(@RequestBody PassEntity p, Principal principal , Model model){
        userServiceImp.updatePass(p,Integer.valueOf(principal.getName()));
        model.addAttribute("user",userServiceImp.getById(Integer.valueOf(principal.getName())));
        return "userpage:: userInfo";
    }


    //上传新商品
    @PostMapping("/user/uploadProduct")
    public String m3(@RequestPart("productinfo")NewProductEntity np, @RequestPart("file") MultipartFile file, Principal principal , Model model) throws IOException {
        System.out.println(np);
        model.addAttribute("addMess",productServiceImp.addNewProduct(np , file ,Integer.valueOf(principal.getName()))?"上传成功，等待审批":"上传失败");
        return "userpage::box4";
    }
}
