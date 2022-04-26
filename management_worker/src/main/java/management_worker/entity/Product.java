package management_worker.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
@TableName
public class Product {
    @TableId
    private Integer productId;

    private Integer userId;
    private Double price;
    private String name;
    private String category;
    private String publishStatus;
    private String auditStatus;
    private String auditTime;
    private String productInform;
    private String applyTime;
    private String brand;
    private Double weight;
    private Double length;
    private Double height;
    private Double width;
    private String pictureUrl;
    private Integer stock;
    private String auditFailInform;
    private String sellTime;

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime.substring(0,19);
    }
}
