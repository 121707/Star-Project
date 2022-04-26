package management_worker.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import management_worker.entity.Product;

public interface ProductService extends IService<Product> {
    //分页查询未审核的商品
    public Page<Product> pageAduitList(Integer curpage);

    //修改审核状态
    public void uAuditStatus(Integer productId , Integer auditStatus);
}
