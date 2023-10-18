package com.bluemsunblog.service;

import com.bluemsunblog.Util.ResponseResultUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Transactional
public interface UserService {
    //根据token查询用户信息
    ResponseResultUtil getUserBytoken(String token);

    ResponseResultUtil uploadPhoto(HttpServletRequest request) throws ServletException, IOException;
}
