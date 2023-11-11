package com.bluemsunblog.service.impl;

import com.bluemsunblog.util.MD5Util;
import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.util.Result;
import com.bluemsunblog.dao.UserDao;
import com.bluemsunblog.entity.User;
import com.bluemsunblog.exception.AppException;
import com.bluemsunblog.exception.AppExceptionCodeMsg;
import com.bluemsunblog.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserDao userDao;
    @Override
    public ResponseResultUtil regiter(User user) {
        if(user.getUserName().equals("")){
            return ResponseResultUtil.result(Result.error3);
        }
        if(user.getUserName().equals("-1111111111")){
            throw new AppException(AppExceptionCodeMsg.USER_ALREADY_EXISTS);
        }
        if(user.getUserPassword().equals("")){
            return ResponseResultUtil.result(Result.error4);
        }
        if(user.getUserEmail().equals("")){
            return ResponseResultUtil.result(Result.error5);
        }
        if(user.getUserPhone().equals("")){
            return ResponseResultUtil.result(Result.error6);
        }
        if(userDao.getUserByusername(user.getUserName())!=null){
//            return ResponseResultUtil.error8(CodeUtil.error8);
            throw new AppException(AppExceptionCodeMsg.USER_ALREADY_EXISTS);
        }
        userDao.register(user.getUserName(), MD5Util.MD5Encode(user.getUserPassword(),"UTF-8"), user.getUserEmail(), user.getUserPhone());
        return ResponseResultUtil.result(Result.ok2);
    }
}
