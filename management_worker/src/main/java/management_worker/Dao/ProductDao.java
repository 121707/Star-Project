package management_worker.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import management_worker.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductDao extends BaseMapper<Product> {
}
