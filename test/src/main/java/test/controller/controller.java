package test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.annotations.Test;
import test.entity.myuser;
import test.mapper.mapper;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class controller {

    @Resource
    mapper mapper;

    @ResponseBody
    @GetMapping("/readlist")
    public Map list(@RequestParam("curpage")int curpage){
        PageHelper.startPage(curpage , 4);
        PageInfo<myuser> pageInfoRes = new PageInfo(mapper.getall());
        Map<String,Object> map = new HashMap<>();
        map.put("list" , pageInfoRes.getList());
        map.put("total" , pageInfoRes.getTotal());
        return map;
    }
    @ResponseBody
    @PostMapping("/removelist")
    public Map remove_list(@RequestParam("curpage")int curpage , @RequestParam("myuser_id")int myuser_id){
        PageHelper.startPage(curpage , 4);
        mapper.deletebyid(myuser_id);
        PageInfo<myuser> pageInfoRes = new PageInfo(mapper.getall());
        Map<String,Object> map = new HashMap<>();
        map.put("list" , pageInfoRes.getList());
        map.put("total" , pageInfoRes.getTotal());
        return map;
    }

    @Test
    public void m2(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    }



    @GetMapping("/list")
    public String m1(){
        return "list";
    }
}
