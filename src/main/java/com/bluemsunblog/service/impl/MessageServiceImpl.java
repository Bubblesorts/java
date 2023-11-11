package com.bluemsunblog.service.impl;

import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.util.Result;
import com.bluemsunblog.dao.MessageDao;
import com.bluemsunblog.dao.UserDao;
import com.bluemsunblog.entity.Friend;
import com.bluemsunblog.entity.Message;
import com.bluemsunblog.entity.User;
import com.bluemsunblog.exception.AppException;
import com.bluemsunblog.exception.AppExceptionCodeMsg;
import com.bluemsunblog.service.LoginService;
import com.bluemsunblog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;
    @Autowired
    LoginService loginService;
    @Autowired
    UserDao userDao;
    @Override
    public ResponseResultUtil message(String token) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        List<Message> list=messageDao.message(userDao.getUserByid(user.getUserId()).getUserName());
        return ResponseResultUtil.result(Result.ok20,list);
    }

    @Override
    public ResponseResultUtil careFriend(String token, Friend friend) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        if(friend.getFriendFuser().equals(userDao.getUserByid(user.getUserId()).getUserName())){
            return ResponseResultUtil.result(Result.error15);
        }else {
            friend.setFriendMuser(userDao.getUserByid(user.getUserId()).getUserName());
            if(userDao.displayCare(friend)==null){
                messageDao.careFriend(friend);
                Message message = new Message();
                message.setMessageMuser(friend.getFriendFuser());
                message.setMessageFuser(friend.getFriendMuser());
                message.setMessageTime(friend.getFriendTime());
                message.setMessageKinds("care");
                messageDao.insert(message);
                return ResponseResultUtil.result(Result.ok20);
            }else {
                messageDao.deleteCare(friend);
                Message message = new Message();
                message.setMessageMuser(friend.getFriendFuser());
                message.setMessageFuser(friend.getFriendMuser());
                message.setMessageTime(friend.getFriendTime());
                message.setMessageKinds("deletecare");
                messageDao.insert(message);
                return ResponseResultUtil.result(Result.ok26);
            }

        }

    }
}
