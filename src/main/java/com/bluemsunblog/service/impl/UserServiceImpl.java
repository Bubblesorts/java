package com.bluemsunblog.service.impl;

import com.bluemsunblog.service.EmailService;
import com.bluemsunblog.util.*;
import com.bluemsunblog.dao.UserDao;
import com.bluemsunblog.entity.Friend;
import com.bluemsunblog.entity.User;
import com.bluemsunblog.exception.AppException;
import com.bluemsunblog.exception.AppExceptionCodeMsg;
import com.bluemsunblog.service.LoginService;
import com.bluemsunblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private LoginService loginService;
    @Autowired
    UserDao userDao;
    @Autowired
    EmailService emailService;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public ResponseResultUtil getUserBytoken(String token) {
        User user = loginService.checkToken(token);
        if(user==null){
            return ResponseResultUtil.result(Result.error8);
        }
        User user1 = userDao.getUserByusername(userDao.getUserByid(user.getUserId()).getUserName());
        return ResponseResultUtil.result(Result.ok3,user1);
    }

    @Override
    public ResponseResultUtil uploadPhoto(HttpServletRequest request, MultipartFile headImage) throws ServletException, IOException {
        String token  = request.getHeader("token");
        User user = loginService.checkToken(token);
        if(user==null){
            return ResponseResultUtil.result(Result.error8);
        }
//        String userPhoto = FileUntil.address(request);
        String userPhoto = FileUntil.uploadImage(headImage,request);
        userDao.uploadPhoto(userDao.getUserByid(user.getUserId()).getUserName(),userPhoto);
        return ResponseResultUtil.result(Result.ok17);
    }

    @Override
    public ResponseResultUtil updateUser(String token, User user) {
        User user1 = loginService.checkToken(token);
        if (user1 == null) {
            return ResponseResultUtil.result(Result.error8);
        }else {
            user.setUserId(user1.getUserId());
            if (user.getStatus().equals("userName")) {
                User user2 = userDao.getUserByusername(user.getUserName());
                if (user2 == null) {
                    userDao.updateUsername(user);
                    return ResponseResultUtil.result(Result.ok20);
                } else {
                    return ResponseResultUtil.result(Result.error7);
                }
            }
            if (user.getStatus().equals("userEmail")) {
                userDao.updateEmail(user);
                return ResponseResultUtil.result(Result.ok20);
            }
            if (user.getStatus().equals("userPhone")) {
                userDao.updatePhone(user);
                return ResponseResultUtil.result(Result.ok20);
            }
            else  {
                String userPassword = MD5Util.MD5Encode(user.getUserPassword(), "UTF-8");
                String userOriginpassword = MD5Util.MD5Encode(user.getOriginPassword(), "UTF-8");
                user.setUserPassword(userPassword);
                user.setOriginPassword(userOriginpassword);
                if (userDao.getUserByid(user.getUserId()).getUserPassword().equals(userOriginpassword)) {
                    userDao.updatePassword(user);
                    return ResponseResultUtil.result(Result.ok20);
                } else {
                    return ResponseResultUtil.result(Result.error2);
                }
            }
        }

    }

    @Override
    public ResponseResultUtil searchUser(String token, User user) {
        User admin = loginService.checkToken(token);
        if(admin==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        if(admin.getUserStatus()==1){
            return ResponseResultUtil.result(Result.error12);
        }
        if(user.getUserName().equals("")){
            List<User> list= userDao.searchallUser(user);
            return ResponseResultUtil.result(Result.ok20,list);
        }else {
            User user1=userDao.getUserByusername(user.getUserName());
            if(user1.getUserStatus()==1){
                List<User> list =new ArrayList<>();
                list.add(user1);
                return ResponseResultUtil.result(Result.ok20,list);
            }else {
                return ResponseResultUtil.result(Result.ok20);
            }

        }

    }

    @Override
    public ResponseResultUtil  myfriend(String token,int status) {
        User user = loginService.checkToken(token);
        if(user==null){
            return ResponseResultUtil.result(Result.error8);
        }
        Friend friend = new Friend();
        friend.setFriendMuser(user.getUserName());
        List<Friend> myfriend = userDao.myfriend(friend);
        List<Friend> list = new ArrayList<>();
        for (int i=0;i<myfriend.size();i++){
            Friend friend1 =new Friend();
            friend1.setFriendMuser(myfriend.get(i).getFriendFuser());
            friend1.setFriendFuser(myfriend.get(i).getFriendMuser());
            if(userDao.displayCare(friend1)!=null){
                list.add(userDao.displayCare(friend1));
            }
        }
        if(status==1){
            return ResponseResultUtil.result(Result.ok20,myfriend);
        }else {
            return ResponseResultUtil.result(Result.ok20,list);
        }

    }

    @Override
    public ResponseResultUtil displayCare(String token,User user) {
        User user1 = loginService.checkToken(token);
        if(user1==null){
            return ResponseResultUtil.result(Result.error8);
        }
        Friend friend=new Friend();
        friend.setFriendMuser(user1.getUserName());
        friend.setFriendFuser(user.getUserName());
        Friend friend1=userDao.displayCare(friend);
        if(friend1==null){
            return ResponseResultUtil.result(Result.ok24);
        }else {
            return ResponseResultUtil.result(Result.ok25);
        }
    }

    @Override
    public ResponseResultUtil email(User user) throws MessagingException {
        if(userDao.getUserByusername(user.getUserName())==null){
            return ResponseResultUtil.result(Result.error16);
        }else {
            if(user.getCode().equals("")){
                String email=userDao.getUserByusername(user.getUserName()).getUserEmail();
                String code1=VerificationCodeUtils.generateCode(6);
                redisTemplate.opsForValue().set("code"+email,code1);
                redisTemplate.expire("code"+email,60, TimeUnit.SECONDS);
                emailService.sendMail(email,"找回密码","您的验证码是"+ code1);
                return ResponseResultUtil.result(Result.ok28);
            }else {
                String email=userDao.getUserByusername(user.getUserName()).getUserEmail();
                if(redisTemplate.opsForValue().get("code"+email)==null){
                    return ResponseResultUtil.result(Result.error17);
                }else {
                    if(redisTemplate.opsForValue().get("code"+email).equals(user.getCode())){
                        return ResponseResultUtil.result(Result.ok29);
                    }else {
                        return ResponseResultUtil.result(Result.error18);
                    }
                }
            }

        }

    }

    @Override
    public ResponseResultUtil updatePwd(User user) {
        String pwd=MD5Util.MD5Encode(user.getUserPassword(),"UTF-8");
        userDao.updatePwd(user.getUserName(),pwd);
        return ResponseResultUtil.result(Result.ok20);
    }

    @Override
    public ResponseResultUtil friendMessage(User user) {
        User userByusername = userDao.getUserByusername(user.getUserName());
        return ResponseResultUtil.result(Result.ok20,userByusername);
    }


}
