package com.bluemsunblog.service.impl;

import com.bluemsunblog.util.*;
import com.bluemsunblog.dao.UserDao;
import com.bluemsunblog.entity.User;
import com.bluemsunblog.exception.AppException;
import com.bluemsunblog.exception.AppExceptionCodeMsg;
import com.bluemsunblog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ResponseResultUtil login(User user) {
        String userName = user.getUserName();
        String userPassword = MD5Util.MD5Encode(user.getUserPassword(),"UTF-8");
        if (userName.equals("") || userPassword.equals("")) {
            return ResponseResultUtil.result(Result.error1);
        }
        User user1 = userDao.getUserByusernameandpassword(userName, userPassword);
        User user2= userDao.getUserByusername(userName);
        if(user2==null){
            throw new AppException(AppExceptionCodeMsg.USER_NOT_EXISTS);
        }else {
            if (user1 == null) {
                return ResponseResultUtil.result(Result.error2);
            }else {
                if(user1.getUserProhibit()==0){
                    return ResponseResultUtil.result(Result.error13);
                }else {
                    String token = JWTUtil.creatToken(user1);
                    redisTemplate.opsForValue().set("token_" + token, com.alibaba.fastjson.JSON.toJSONString(user1), 7, TimeUnit.DAYS);
                    return ResponseResultUtil.result(Result.ok,token);
                }

            }
        }
    }

    @Override
    public User checkToken(String token) {
        if (token.equals("")||token.equals("null")) {
            return null;
        }
        Map<String, Object> map = JWTUtil.checkToken(token);
        if (map == null) {
            return null;
        }
        String s = redisTemplate.opsForValue().get("token_" + token);
        if (s.equals("")) {
            return null;
        }
        User user = com.alibaba.fastjson2.JSON.parseObject(s, User.class);
        return user;
    }

    @Override
    public ResponseResultUtil logout(String token) {
        redisTemplate.delete("token_"+token);
        return ResponseResultUtil.result(Result.ok1);
    }
}
