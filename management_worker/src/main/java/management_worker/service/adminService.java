package management_worker.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import management_worker.entity.AddAdminEntity;
import management_worker.entity.Admin;
import management_worker.entity.SelectAdminEntity;
import management_worker.entity.PageEntity;

public interface adminService extends IService<Admin> {
    //查询管理员的身份
    public String getTypeById(Integer adminId);

    //分页查询
    public Page<Admin> pageList(PageEntity<SelectAdminEntity> pe);

    //Id删除
    public boolean removeById(Integer id);

    //添加新admin
    public Integer addAdmin(AddAdminEntity aae);
}
