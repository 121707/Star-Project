package management_worker.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import management_worker.entity.AddAdminEntity;
import management_worker.entity.Admin;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface AdminDao extends BaseMapper<Admin> {
    //获取该用户的所有权限
    @Select("select admin_type from admin where admin_Id = #{adminId}")
    public String getTypeById(@Param("adminId") Integer adminId);

    @Delete("delete from admin where admin_id = #{adminId}")
    public boolean removeById(@Param("adminId") Integer adminId);

    //新增admin
    @Insert("insert into admin(admin_password , employee_id ,admin_type, register_time, " +
            "register_id , phone , admin_status ) value(#{a.adminPassword} , #{a.employeeId}, #{a.adminType} ," +
            " #{a.registerTime} ,#{a.registerId}, #{a.phone} , #{a.adminStatus} )")
    public boolean addAdmin(@Param("a") Admin a);

    //获取新增的admin_id
    @Select("select max(admin_id) from admin")
    public Integer getNewAdmin();
}
