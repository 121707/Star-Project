package com.example.user_service.entity;

import lombok.Data;

@Data
public class OrderInfoEntity {
    private Integer productId;
    private Integer buyerId;
    private Integer sellerId;
}
