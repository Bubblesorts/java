package com.bluemsunblog.service;

import com.bluemsunblog.Util.ResponseResultUtil;
import com.bluemsunblog.entity.Blogs;
import com.bluemsunblog.entity.Comment;
import com.bluemsunblog.entity.Like;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BolgsService {
    ResponseResultUtil deliver(String token,Blogs blogs);

    ResponseResultUtil deliverComment(String token, Comment comment);

    ResponseResultUtil blogsLike(String token, Like like);

    ResponseResultUtil displayLike(String token, Blogs blogs);

    ResponseResultUtil diaplayComment(String token, Comment comment);

    ResponseResultUtil commentLike(String token, Comment comment);

    ResponseResultUtil diaplayCommentlike(String token, Comment comment);

    ResponseResultUtil collectBlogs(String token, Blogs blogs);

    ResponseResultUtil myCollect(String token);

    ResponseResultUtil blogsMessage(Blogs blogs);

    ResponseResultUtil myBlogs(String token);

    ResponseResultUtil blogsKinds(Blogs blogs);

    ResponseResultUtil searchBlogs(Blogs blogs);
}
