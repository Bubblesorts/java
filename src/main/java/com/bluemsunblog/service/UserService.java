package com.bluemsunblog.service;

import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.entity.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Transactional
public interface UserService {
    //根据token查询用户信息
    ResponseResultUtil getUserBytoken(String token);

    ResponseResultUtil uploadPhoto(HttpServletRequest request, MultipartFile headImage) throws ServletException, IOException;

    ResponseResultUtil updateUser(String token, User user);

    ResponseResultUtil searchUser(String token, User user);

    ResponseResultUtil myfriend(String token,int status);

    ResponseResultUtil displayCare(String token,User user);

    ResponseResultUtil email(User user) throws MessagingException;

    ResponseResultUtil updatePwd(User user);

    ResponseResultUtil friendMessage(User user);
}
