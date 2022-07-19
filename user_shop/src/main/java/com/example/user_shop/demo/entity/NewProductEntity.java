package com.example.user_shop.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class NewProductEntity implements Serializable {
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Integer stock;
    private String sellTime;
    private String productInform;
}
