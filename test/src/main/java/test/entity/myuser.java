package test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName
public class myuser {
    int id;
    String name;
    int age;
}
