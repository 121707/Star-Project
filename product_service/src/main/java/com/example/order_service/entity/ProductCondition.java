package com.example.order_service.entity;

import lombok.Data;

@Data
public class ProductCondition {
    String key;
    Integer maxPrice;
    Integer minPrice;
}
