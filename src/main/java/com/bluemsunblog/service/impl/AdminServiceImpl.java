package com.bluemsunblog.service.impl;

import com.bluemsunblog.entity.*;
import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.util.Result;
import com.bluemsunblog.dao.AdminDao;
import com.bluemsunblog.dao.BlogsDao;
import com.bluemsunblog.dao.MessageDao;
import com.bluemsunblog.dao.UserDao;
import com.bluemsunblog.exception.AppException;
import com.bluemsunblog.exception.AppExceptionCodeMsg;
import com.bluemsunblog.service.AdminService;
import com.bluemsunblog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    LoginService loginService;
    @Autowired
    UserDao userDao;
    @Autowired
    AdminDao adminDao;
    @Autowired
    BlogsDao blogsDao;
    @Autowired
    MessageDao messageDao;
    @Autowired
    RedisTemplate redisTemplate;
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
            adminDao.prohibit(0, user.getUserName());
            return ResponseResultUtil.result(Result.ok21);
        }else {
            adminDao.prohibit(1, user.getUserName());
            return ResponseResultUtil.result(Result.ok22);
        }
    }

    @Override
    public ResponseResultUtil checkdai(String token) {
        User admin = loginService.checkToken(token);
        if(admin==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        if(admin.getUserStatus()==1){
            return ResponseResultUtil.result(Result.error12);
        }
        List<Examine> list = adminDao.checkdai();
        return ResponseResultUtil.result(Result.ok20,list);
    }

    @Override
    public ResponseResultUtil checkdaimsg(String token, Examine examine) {
        User admin = loginService.checkToken(token);
        if(admin==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        if(admin.getUserStatus()==1){
            return ResponseResultUtil.result(Result.error12);
        }
        Examine checkdaimsg = adminDao.checkdaimsg(examine);
        return ResponseResultUtil.result(Result.ok20,checkdaimsg);
    }

    @Override
    public ResponseResultUtil examineBlogs(String token, Examine examine) throws ParseException {
        User admin = loginService.checkToken(token);
        if(admin==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }

            if(admin.getUserStatus()==1){
                return ResponseResultUtil.result(Result.error12);
            }
            int c=0;
            List<Examine> checkdai = adminDao.checkdai();
            for(int i=0;i<checkdai.size();i++){
                if(checkdai.get(i).getExamineId()==examine.getExamineId()){
                    c++;
                }
            }
            if(c==1){
                if(examine.getExamineStatus()==1){
                    Blogs blogs = new Blogs();
                    Examine checkdaimsg = adminDao.checkdaimsg(examine);
                    blogs.setBlogsUsername(checkdaimsg.getExamineUsername());
                    blogs.setBlogsIntro(checkdaimsg.getExamineIntro());
                    blogs.setBlogsKinds(checkdaimsg.getExamineKinds());
                    blogs.setBlogsName(checkdaimsg.getExamineBlogsname());
                    blogs.setBlogsPhoto(checkdaimsg.getExaminePhoto());
                    blogs.setBlogsFile(checkdaimsg.getExamineFile());
                    blogs.setBlogsTime(checkdaimsg.getExamineTime());
                    blogs.setBlogsTop(0);
                    adminDao.examineBlogs(examine);
                    adminDao.examinesuccess(blogs);
                    Message message=new Message();
                    message.setMessageMuser(adminDao.checkdaimsg(examine).getExamineUsername());
                    message.setMessageFuser(admin.getUserName());
                    message.setMessageKinds("审核通过");
                    message.setMessageIntro(adminDao.checkdaimsg(examine).getExamineBlogsname());
                    messageDao.insert(message);
                    return ResponseResultUtil.result(Result.ok20);
                }else {
                    adminDao.examineBlogs(examine);
                    Message message=new Message();
                    message.setMessageMuser(adminDao.checkdaimsg(examine).getExamineUsername());
                    message.setMessageFuser(admin.getUserName());
                    message.setMessageKinds("审核不通过");
                    message.setMessageIntro(adminDao.checkdaimsg(examine).getExamineBlogsname());
                    messageDao.insert(message);
                    return ResponseResultUtil.result(Result.error14);
                }
            }else {
                return ResponseResultUtil.result(Result.ok27);
            }

    }

    @Override
    public ResponseResultUtil topBlogs(String token, Blogs blogs) {
        User admin = loginService.checkToken(token);
        if(admin==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        if(admin.getUserStatus()==1){
            return ResponseResultUtil.result(Result.error12);
        }
        if(blogsDao.getblogsByid(blogs.getBlogsId()).getBlogsTop()==0){
            adminDao.topBlogs(blogs);
            return ResponseResultUtil.result(Result.ok20);
        }else {
            adminDao.topBlogs0(blogs);
            return ResponseResultUtil.result(Result.ok23);
        }

    }

    @Override
    public ResponseResultUtil systemNotice(String token, String notice) {
        User admin = loginService.checkToken(token);
//        if(admin==null){
//            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
//        }

        if(notice.equals("")){
            return ResponseResultUtil.result(Result.ok20,redisTemplate.opsForValue().get("systemNotice"));
        }else {
            if(admin.getUserStatus()==1){
            return ResponseResultUtil.result(Result.error12);
        }
            redisTemplate.opsForValue().set("systemNotice",notice);
            return ResponseResultUtil.result(Result.ok20);
        }


    }

    @Override
    public ResponseResultUtil checkTop(String token) {
        User admin = loginService.checkToken(token);
        if(admin==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        if(admin.getUserStatus()==1){
            return ResponseResultUtil.result(Result.error12);
        }
        List<Blogs> list = adminDao.checkTop();
        return ResponseResultUtil.result(Result.ok20,list);
    }

    @Override
    public ResponseResultUtil kinds(Kinds kinds) {
        if(kinds.getBlogsKinds().equals("")){
            List<Kinds> list=adminDao.allKinds();
            return ResponseResultUtil.result(Result.ok20,list);
        }else {
            adminDao.addKinds(kinds);
            return ResponseResultUtil.result(Result.ok20);
        }
    }
}
