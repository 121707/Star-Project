package com.example.user_shop.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.user_shop.demo.entity.Product;
import com.example.user_shop.demo.service.Imp.OrderServiceImp;
import com.example.user_shop.demo.service.Imp.ProductServiceImp;
import com.example.user_shop.demo.service.Imp.UserServiceImp;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class view {

    @Autowired
    private ProductServiceImp imp;

    @Autowired
    private OrderServiceImp orderServiceImp;

    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("/shop/main")
    public String m1(HttpServletRequest request, Model model , Principal principal){
        model.addAttribute("user",userServiceImp.getById(principal.getName()));
        System.out.println(model.getAttribute("user"));
        return "list";
    }

    //跳转商品展示页
    @GetMapping("/shop/show/{productId}")
    public String m2( @PathVariable("productId")Integer productId, Model model , Principal principal){
        model.addAttribute("user",userServiceImp.getById(principal.getName()));
        Product p = imp.getById(productId);
        model.addAttribute("p" , p);
        return "infor";
    }

//    //跳转购买成功页
//    @GetMapping("/shop/paysucess/{productId}")
//    public String m3(@PathVariable("productId")Integer productId, Model model ,Principal principal) throws ParseException {
//        Product p = imp.getById(productId);
//        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Long time = (new Date().getTime() - sd.parse(p.getSellTime()).getTime()) / 60 / 1000L;
//        Integer uId = Integer.valueOf(principal.getName());
//        String failUrl = "forward:/shop/show/"+productId;
//
//        if(time < 20){
//            if(!imp.subRedisStockByKey(1 , productId))return failUrl;
//        }else {
//            if(!imp.subStockByKey(1 , productId))return failUrl;
//        }
//
//        orderServiceImp.addNewOrder(productId , uId);
//
//        model.addAttribute("user",userServiceImp.getById(principal.getName()));
//        model.addAttribute("productId" , productId);
//
//        return "paysucess";
//    }


    //跳转秒杀也
    @GetMapping("/shop/seckill")
    public String m4(Model model ,Principal principal){
        model.addAttribute("user",userServiceImp.getById(principal.getName()));
        return "seckilllist";
    }

    //用户页
    @GetMapping("/shop/userpage")
    public String m5(Model model ,Principal principal){
        QueryWrapper<Product> q = new QueryWrapper<>();
        Integer userId = Integer.valueOf(principal.getName());
        Integer[] order = orderServiceImp.getOrderNumById(userId);
        q.eq("user_id",userId);

        model.addAttribute("user",userServiceImp.getById(userId));
        model.addAttribute("input",orderServiceImp.inputMonth(userId));
        model.addAttribute("sellNum",order[1]);
        model.addAttribute("buyNum",order[0]);
        model.addAttribute("publishNum",imp.count(q));
        return "userpage";
    }




}
