package management_worker.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import management_worker.Dao.AdminDao;
import management_worker.entity.AddAdminEntity;
import management_worker.entity.Admin;
import management_worker.entity.SelectAdminEntity;
import management_worker.entity.PageEntity;
import management_worker.service.adminService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class adminServiceImp extends ServiceImpl<AdminDao, Admin> implements adminService {
   @Autowired
   AdminDao admin_dao;

   @Value("${admin.adminListSize}")
   private int size;

    @Override
    public boolean removeById(Integer adminId) {
       boolean res = admin_dao.removeById(adminId);
        System.out.println(res?"删除成功":"删除失败");
        return res;
    }

    @Override
    public Integer addAdmin(AddAdminEntity aae) {
        //定义参数承载实例对
        Admin admin = new Admin();
        //获取注册时间
        DateTime dt = new DateTime();
        String registerDate = dt.toString("yy-MM-dd");
        //密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(aae.getAdminPassword());

        admin.setAdminPassword(password).setAdminStatus(aae.getAdminStatus())
        .setAdminType(aae.getAdminType()).setEmployeeId(aae.getEmployeeId()).setRegisterId(aae.getRegisterId())
        .setPhone(aae.getPhone()).setRegisterTime(registerDate);

        return admin_dao.addAdmin(admin) ? admin_dao.getNewAdmin() : null;
    }

    @Override
    public String getTypeById(Integer adminId) {
       return admin_dao.getTypeById(adminId);
    }

    @Override
    public Page<Admin> pageList(PageEntity<SelectAdminEntity> pe) {
        SelectAdminEntity ace = pe.getEntity();
        Integer curpage = pe.getCurpage(), adminId = ace.getAdminId() , employeeId = ace.getEmployeeId() ,
                registerId = ace.getRegisterId();
        String adminType = ace.getAdminType() , lastLoginTimeFirst = ace.getLastLoginTimeFirst(),
                lastLoginTimeLast = ace.getLastLoginTimeLast() , registerTimeLast = ace.getRegisterTimeLast(),
                registerTimeFirst = ace.getRegisterTimeFirst(),adminStatus = ace.getAdminStatus();

        QueryWrapper<Admin> qw = new QueryWrapper<>();
        Page<Admin> page = new Page(curpage,10);

        if(adminId != null)qw.eq("admin_id" , adminId);
        if(employeeId != null)qw.eq("employee_id" , employeeId);
        if(registerId != null)qw.eq("register_id" , registerId);
        if(adminType.length() > 0)qw.eq("admin_type",adminType);
        if(adminStatus.length() > 0)qw.eq("admin_status" , adminStatus);
        if(lastLoginTimeFirst.length() > 0 && lastLoginTimeLast.length() > 0)
            qw.between("last_login_time" , lastLoginTimeFirst , lastLoginTimeLast);
        if(registerTimeFirst.length() > 0 && registerTimeLast.length() > 0)
            qw.between("register_time" , registerTimeFirst , registerTimeLast);

        return this.page(page , qw);
    }

}
