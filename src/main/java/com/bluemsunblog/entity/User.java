package com.bluemsunblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = -1242493306307174690L;
    //用户id
    private int userId;
    //用户名
    private String userName;
    //用户密码
    private String userPassword;
    //原始密码
    private String originPassword;
    private int userStatus;//2管理员
    private String userEmail;
    private String userPhone;
    private String userPhoto;
    private int userProhibit;
    private int userCollect;
    private int userMyblogs;
    private int userMylike;
    private int userMycollect;
    private String code;
    private String status;
}
