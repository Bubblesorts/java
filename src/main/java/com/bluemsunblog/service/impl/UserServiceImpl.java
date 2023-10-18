package com.bluemsunblog.service.impl;

import com.bluemsunblog.Util.FileUntil;
import com.bluemsunblog.Util.ResponseResultUtil;
import com.bluemsunblog.Util.Result;
import com.bluemsunblog.dao.UserDao;
import com.bluemsunblog.entity.User;
import com.bluemsunblog.service.LoginService;
import com.bluemsunblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private LoginService loginService;
    @Autowired
    UserDao userDao;
    @Override
    public ResponseResultUtil getUserBytoken(String token) {
        User user = loginService.checkToken(token);
        if(user==null){
            return ResponseResultUtil.result(Result.error8);
        }
        User user1 = userDao.getUserByusername(user.getUserName());
        return ResponseResultUtil.result(Result.ok3,user1);
    }

    @Override
    public ResponseResultUtil uploadPhoto(HttpServletRequest request) throws ServletException, IOException {
        String token  = request.getHeader("token");
        User user = loginService.checkToken(token);
        if(user==null){
            return ResponseResultUtil.result(Result.error8);
        }
        String userPhoto = FileUntil.address(request);
        userDao.uploadPhoto(user.getUserName(),userPhoto);
        return ResponseResultUtil.result(Result.ok17);
    }


}
