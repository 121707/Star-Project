package management_worker.controller;

import management_worker.service.Imp.adminServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class view {
    @Autowired
    adminServiceImp adminServiceImp;

    //后台主页面
    @GetMapping("/main")
    public String main(Principal principal , Model model){
        model.addAttribute("adminId",principal.getName());
        return "main";
    }

    //用户信息查询table
    @GetMapping("/main/userlist")
    public String userlist(){
        return "user_list";
    }

    //管理员登录界面
    @GetMapping("/adminlogin")
    public String admin_login(){
        return "login";
    }

    //添加管理员
    @GetMapping("/main/adminlist")
    public String admin_list(){
        return "admin_list";
    }

    //审核列表
    @GetMapping("/main/auditlist")
    public String auditList(){
        return "AuditList";
    }

}
