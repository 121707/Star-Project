package test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import test.entity.myuser;

import java.util.List;

@Mapper
public interface mapper extends BaseMapper<myuser> {
    @Select("select * from myuser")
    public List<myuser> getall();

    @Delete("delete from myuser where id = #{id}")
    public void deletebyid(int id);
}
