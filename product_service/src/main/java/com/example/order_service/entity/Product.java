package com.example.order_service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName
public class Product {
    @TableId(type = IdType.AUTO)
    private Integer productId;

    private Integer userId;
    private Double price;
    private String name;
    private String category;
    private String publishStatus;
    private String auditStatus;
    private String auditTime;
    private String productInform;
    private String brand;
    private Double weight;
    private Double length;
    private Double height;
    private Double width;
    private String pictureUrl;
    private Integer stock;
    private String auditFailInform;
    private String sellTime;
    private String applyTime;

    public void setSellTime(String sellTime) {
        if(sellTime.length() >= 19)this.sellTime = sellTime.substring(0,19);
        else this.sellTime = sellTime;
    }
}
