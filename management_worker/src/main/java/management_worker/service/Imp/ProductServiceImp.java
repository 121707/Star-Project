package management_worker.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import management_worker.Dao.ProductDao;
import management_worker.entity.Product;
import management_worker.service.ProductService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class ProductServiceImp extends ServiceImpl<ProductDao, Product> implements ProductService {
    @Value("${page.size}")
    private Integer pageSize;

    @Override
    public Page<Product> pageAduitList(Integer curpage) {
        Page<Product> page = new Page<>(curpage , pageSize);
        QueryWrapper<Product> q = new QueryWrapper<>();
        q.eq("audit_status" , 0);

        return this.page(page,q);
    }

    //审批结果修改状态
    @Override
    public void uAuditStatus(Integer productId ,Integer auditStatus) {
        //auditStatus 1为失败， 2为成功 ，成功即上架
        UpdateWrapper<Product> u = new UpdateWrapper<>();
        DateTime dateTime = new DateTime();

        u.eq("product_id",productId);
        if(auditStatus == 2)u.set("publish_status",1);
        u.set("audit_status",auditStatus);
        u.set("audit_time",dateTime.toString("yy-MM-dd hh:mm:ss"));
        this.update(u);
    }
}
