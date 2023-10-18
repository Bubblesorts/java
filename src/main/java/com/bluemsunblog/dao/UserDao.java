package com.bluemsunblog.dao;

import com.bluemsunblog.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    //通过用户名和密码查询用户
    User getUserByusernameandpassword(String userName,String userPassword);
    //通过用户名查询用户
    User getUserByusername(String userName);
    //注册
    void register(String userName,String userPassword,String userEmail,String userPhone);
    //上传头像
    void uploadPhoto(String userName,String userPhoto);
}
