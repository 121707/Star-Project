package com.example.order_service.entity;

import lombok.Data;

@Data
public class NewProductEntity {
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Integer stock;
    private String sellTime;
    private String productInform;
}
