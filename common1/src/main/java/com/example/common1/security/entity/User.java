package com.example.common1.security.entity;



import lombok.Data;

import java.io.Serializable;

@Data

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String passWord;


}

