package com.bluemsunblog.service.impl;

import com.bluemsunblog.Util.ResponseResultUtil;
import com.bluemsunblog.Util.Result;
import com.bluemsunblog.dao.AdminDao;
import com.bluemsunblog.dao.UserDao;
import com.bluemsunblog.entity.User;
import com.bluemsunblog.exception.AppException;
import com.bluemsunblog.exception.AppExceptionCodeMsg;
import com.bluemsunblog.service.AdminService;
import com.bluemsunblog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    LoginService loginService;
    @Autowired
    UserDao userDao;
    @Autowired
    AdminDao adminDao;
    @Override
    public ResponseResultUtil prohibit(String token, User user) {
        User admin = loginService.checkToken(token);
        if(admin==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        if(admin.getUserStatus()==1){
            return ResponseResultUtil.result(Result.error12);
        }
        if (userDao.getUserByusername(user.getUserName()).getUserProhibit()==1){
            adminDao.prohibit(0);
            return ResponseResultUtil.result(Result.ok21);
        }else {
            adminDao.prohibit(1);
            return ResponseResultUtil.result(Result.ok22);
        }
    }
}
