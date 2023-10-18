package com.bluemsunblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    //用户id
    private int userId;
    //用户名
    private String userName;
    //用户密码
    private String userPassword;
    private int userStatus;
    private String userEmail;
    private String userPhone;
    private String userPhoto;
    private int userProhibit;
}
