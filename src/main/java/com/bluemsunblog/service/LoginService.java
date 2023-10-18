package com.bluemsunblog.service;

import com.bluemsunblog.Util.ResponseResultUtil;
import com.bluemsunblog.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoginService {
    //登录
    ResponseResultUtil login(User user);
    //验证token
    User checkToken(String token);
    //登出
    ResponseResultUtil logout(String token);
}
