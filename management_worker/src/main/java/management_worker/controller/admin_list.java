package management_worker.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import management_worker.entity.AddAdminEntity;
import management_worker.entity.Admin;
import management_worker.entity.SelectAdminEntity;
import management_worker.entity.PageEntity;
import management_worker.service.Imp.adminServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class admin_list {
    @Autowired
    private adminServiceImp adminServiceImp;

    //保存查询条件，避免多次重复发送冗余参数；
    private PageEntity<SelectAdminEntity> pageEntityTemp;

    //分页查询
    @PostMapping("/admin/pagelist")
    @ResponseBody
    public Map<String,Object> pageList(@RequestBody PageEntity<SelectAdminEntity> pe){
        pageEntityTemp = pe;
        Map<String,Object> ans = new HashMap<>();
        Page<Admin> page = adminServiceImp.pageList(pe);
        ans.put("list" , page.getRecords());
        ans.put("total" , page.getTotal());
        Arrays.asList();

        return ans;
    }

//    删除后继续分页查询
    @PostMapping("/admin/removebyid")
    @ResponseBody
    public void removeById(@RequestParam("adminId")String adminId){
        adminServiceImp.removeById(adminId);
    }

//    新增admin刷新列表
    @PostMapping("/admin/addadmin")
    @ResponseBody
    public void addAdmin(@RequestBody AddAdminEntity aae , Principal principal){
        aae.setRegisterId(Integer.valueOf(principal.getName()));
        System.out.println(adminServiceImp.addAdmin(aae) != null ? "添加成功" : "添加失败");
    }

}
