package com.example.order_service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Orders {
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    private Integer sellerId;
    private Integer productId;
    private Integer buyerId;
    private String foundTime;
    private Double price;

    public void setFoundTime(String foundTime) {
        this.foundTime = foundTime.substring(0 , 19);
    }
}
