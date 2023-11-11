package com.bluemsunblog.dao;

import com.bluemsunblog.entity.Friend;
import com.bluemsunblog.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    void update(User user);
    User getUserByid(int userId);

    void updateCollect(User user);
    void updateMyblogs(int userMyblogs,String userName);

    void updateLike(String userName, int userMylike);

    void updateMycollect(String userName, int userMycollect);

//    List<User> searchUser(User user);

    List<User> searchallUser(User user);

    List<Friend> myfriend(Friend friend);

    Friend displayCare(Friend friend);

    void updatePwd(String userName,String userPassword);

    void updateUsername(User user);

    void updateEmail(User user);

    void updatePhone(User user);

    void updatePassword(User user);
}
