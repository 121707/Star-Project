package management_worker.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import management_worker.entity.User;
import management_worker.service.Imp.UserServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class controller {


    @Resource
    UserServiceImp userServiceImp;


//    @GetMapping("/security")
//    public String security() {
//        return "security";
//    }

    //分页数据全查询
    @ResponseBody
    @GetMapping("/user/getlist")
    public Map<String ,Object> m1(@RequestParam("curpage") int curpage){
        Page<User> page = new Page<>(curpage,10);
        Map<String ,Object> map = new HashMap<>();
        Page<User> list = userServiceImp.page(page,null);
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return map;
    }

//    分页数据 条件查询
    @ResponseBody
    @PostMapping("/user/selectlist")
    public Map<String,Object> m5(@RequestParam("userId")Integer userID , @RequestParam("userName")String userName, @RequestParam("lastLoginTimeFirst")String lastLoginTimeFirst ,
                                  @RequestParam("registerTimeFirst")String registerTimeFirst, @RequestParam("registerTimeLast")String registerTimeLasr
                               , @RequestParam("lastLoginTimeLast")String lastLoginTimeLast, @RequestParam("userType")String userType,
                                   @RequestParam("userStatus")String userStatus , @RequestParam("curpage")int curpage){
        Map<String,Object> map = new HashMap<>();
        Page<User> list = userServiceImp.SelectBySome(userID,userName,userType,userStatus,lastLoginTimeFirst,
                        registerTimeLasr,registerTimeFirst,lastLoginTimeLast,curpage);
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return map;
    }


    //移除查询
    @ResponseBody
    @GetMapping("/user/remove")
    public Map<String ,Object> m2( @RequestParam("curpage") int curpage , @RequestParam("userId") int user_id){
        System.out.println(userServiceImp.removeById(user_id));
        return m1(curpage);
    }

    //id查询
    @ResponseBody
    @GetMapping("/user/selectbyid")
    public User m3(@RequestParam("userId") int user_id){
        return  userServiceImp.getById(user_id);
    }

    //更新操作
    @ResponseBody
    @PostMapping("/user/update")
    public Map<String ,Object> m4(@RequestBody User user , @RequestParam("curpage") int curpage , Model model){
        model.addAttribute("msg", userServiceImp.update(user));
        return m1(curpage);
    }

    //更换头像
    @ResponseBody
    @PostMapping("/user/updateavatar")
    public void updateavatar(@RequestParam(value = "avatar",required = false)MultipartFile avatar,@RequestParam("curpage")int curpage,
                             @RequestParam("userId")int userId) throws IOException {
        //需要替换文件
        if(avatar != null ){
            String oldUrl = userServiceImp.getAvatarUrlById(userId);
            String originalFileName = avatar.getOriginalFilename();
            String suffix = originalFileName.substring(originalFileName.lastIndexOf('.'));
            String newUrl = "/" + userId + String.valueOf(UUID.randomUUID()) + suffix;
            userServiceImp.replaceFile(oldUrl,newUrl,avatar);
            userServiceImp.updateAvatar(newUrl,userId);
        }
    }
    @ResponseBody
    @PostMapping("/user/adduser")
    public Map<String,Object> adduser(@RequestBody User user, Model model){
        System.out.println(userServiceImp.adduser(user));
        model.addAttribute("msg","15");
        return m1(1);
    }



}
