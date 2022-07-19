package com.example.user_service.entity;

import lombok.Data;

@Data
public class PageEntity<T> {
    private Integer curpage;

    private T entity;
}
