package management_worker.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import management_worker.entity.Product;
import management_worker.service.Imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductList {
    @Autowired
    ProductServiceImp psi;

    @PostMapping("/product/pageAuditList")
    public Map<String,Object> pageAduitList(@RequestParam("curpage") Integer curpage){
        Map<String , Object> ans = new HashMap<>();
        Page<Product> page = psi.pageAduitList(curpage);
        ans.put("list",page.getRecords());
        ans.put("total",page.getTotal());

        return ans;
    }

    @PostMapping("/product/getByPid")
    public Product getByPid(@RequestParam("productId") Integer productId){
        Product p = psi.getById(productId);
        return p;
    }

    //审批结果修改状态
    @PostMapping("/product/uAuditStatus")
    public void uAuditStatus(@RequestParam("productId") Integer productId , @RequestParam("auditStatus") Integer auditStatus){
        psi.uAuditStatus(productId , auditStatus);
    }
}
