package com.hu.video.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private Integer id;

    private String name;

    private String password;

    Date birth;

    String sex;
//    Character sex;

}
