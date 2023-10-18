package com.bluemsunblog.service.impl;

import com.bluemsunblog.Util.ResponseResultUtil;
import com.bluemsunblog.Util.Result;
import com.bluemsunblog.dao.BlogsDao;
import com.bluemsunblog.entity.*;
import com.bluemsunblog.exception.AppException;
import com.bluemsunblog.exception.AppExceptionCodeMsg;
import com.bluemsunblog.service.BolgsService;
import com.bluemsunblog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BolgsServiceImpl implements BolgsService {
    @Autowired
    LoginService loginService;
    @Autowired
    BlogsDao blogsDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public ResponseResultUtil deliver(String token,Blogs blogs) {
        if(blogs.getBlogsName().equals("")){
            return ResponseResultUtil.result(Result.error9);
        }
        if(blogs.getBlogsIntro().equals("")){
            return ResponseResultUtil.result(Result.error10);
        }
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        blogs.setBlogsUsername(user.getUserName());
        blogsDao.deliver(blogs);
        return ResponseResultUtil.result(Result.ok4);
    }

    @Override
    public ResponseResultUtil deliverComment(String token, Comment comment) {
        if(comment.getCommentComment().equals("")){
            return ResponseResultUtil.result(Result.error11);
        }
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        comment.setCommentUsername(user.getUserName());
        blogsDao.deliverComment(comment);
        return ResponseResultUtil.result(Result.ok5);
    }

    @Override
    public ResponseResultUtil blogsLike(String token, Like like) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        like.setLikeUsername(user.getUserName());
        String key = String.valueOf(like.getBlogsId())+like.getLikeUsername();
        Object o = redisTemplate.opsForValue().get(key);
        if(o==null){
            redisTemplate.opsForValue().set(key,like.getLike());
            return ResponseResultUtil.result(Result.ok6);
        }else {
            redisTemplate.delete(key);
            return ResponseResultUtil.result(Result.ok8);
        }

    }

    @Override
    public ResponseResultUtil displayLike(String token, Blogs blogs) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        String key = String.valueOf(blogs.getBlogsId())+user.getUserName();
        Object o = redisTemplate.opsForValue().get(key);
        return ResponseResultUtil.result(Result.ok7,o);
    }

    @Override
    public ResponseResultUtil diaplayComment(String token, Comment comment) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        comment.setCommentUsername(user.getUserName());
        List<Comment> comments = blogsDao.displayComment(comment);
        return ResponseResultUtil.result(Result.ok9,comments);
    }

    @Override
    public ResponseResultUtil commentLike(String token, Comment comment) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        comment.setCommentUsername(user.getUserName());
        String key = String.valueOf(comment.getCommentBlogsid())+comment.getCommentUsername()+comment.getCommentMasteruser();
        if(redisTemplate.opsForHash().get(key,comment.getCommentId())==null)
        {
            redisTemplate.opsForHash().put(key,comment.getCommentId(),comment.getCommentId());
            return ResponseResultUtil.result(Result.ok10);
        }else {
            redisTemplate.opsForHash().delete(key,comment.getCommentId());
            return ResponseResultUtil.result(Result.ok11);
        }
    }

    @Override
    public ResponseResultUtil diaplayCommentlike(String token, Comment comment) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        comment.setCommentUsername(user.getUserName());
        String key = String.valueOf(comment.getCommentBlogsid())+comment.getCommentUsername()+comment.getCommentMasteruser();

        return ResponseResultUtil.result(Result.ok12,redisTemplate.opsForHash().keys(key));
    }

    @Override
    public ResponseResultUtil collectBlogs(String token, Blogs blogs) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        String key = "collect"+user.getUserName();
        if(redisTemplate.opsForHash().get(key,blogs.getBlogsId())==null){
            redisTemplate.opsForHash().put(key,blogs.getBlogsId(),blogs.getBlogsId());
            return ResponseResultUtil.result(Result.ok13);
        }else {
            redisTemplate.opsForHash().delete(key,blogs.getBlogsId());
            return ResponseResultUtil.result(Result.ok14);
        }

    }

    @Override
    public ResponseResultUtil myCollect(String token) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        String key = "collect"+user.getUserName();
        return ResponseResultUtil.result(Result.ok15,redisTemplate.opsForHash().keys(key));
    }

    @Override
    public ResponseResultUtil blogsMessage(Blogs blogs) {
        return ResponseResultUtil.result(Result.ok16,blogsDao.getblogsByid(blogs.getBlogsId()));
    }

    @Override
    public ResponseResultUtil myBlogs(String token) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        List<Blogs> list = blogsDao.myBlogs(user.getUserName());
        return ResponseResultUtil.result(Result.ok18,list);
    }

    @Override
    public ResponseResultUtil blogsKinds(Blogs blogs) {
        if(blogs.getBlogsKinds().equals("")){
            blogs.setBlogsKinds("其他");
        }
        List<Blogs> list = blogsDao.blogsKinds(blogs);
        return ResponseResultUtil.result(Result.ok19,list);
    }

    @Override
    public ResponseResultUtil searchBlogs(Blogs blogs) {
        if(blogs.getBlogsName().equals("")){
            List<Blogs> list = blogsDao.getAllblogs();
            return ResponseResultUtil.result(Result.ok20,list);
        }else {
            List<Blogs> list = blogsDao.getBlogslike(blogs.getBlogsName());
            return ResponseResultUtil.result(Result.ok20,list);
        }
    }
}
