package com.bluemsunblog.service;

import com.bluemsunblog.entity.Drafts;
import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.entity.Blogs;
import com.bluemsunblog.entity.Comment;
import com.bluemsunblog.entity.Like;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Transactional
public interface BolgsService {
    ResponseResultUtil deliver(String token, HttpServletRequest request, MultipartFile headImage1,MultipartFile headImage2) throws ServletException, IOException;

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

    ResponseResultUtil deleteBlogs(String token, Blogs blogs);

//    ResponseResultUtil blogsPhoto(HttpServletRequest request) throws ServletException, IOException;

    ResponseResultUtil hotBlogs(Blogs blogs);

    ResponseResultUtil hotPush();

//    ResponseResultUtil blogsFile(HttpServletRequest request, Blogs blogs) throws ServletException, IOException;

    ResponseResultUtil displayCollect(String token, Blogs blogs);

    ResponseResultUtil careBlogs(String token);

    ResponseResultUtil commentCount(Blogs blogs);

    ResponseResultUtil download(Blogs blogs, HttpServletResponse response) throws IOException;

    ResponseResultUtil code(Map map);

    ResponseResultUtil drafts( String token,HttpServletRequest request,MultipartFile headImage1,MultipartFile headImage2);

    ResponseResultUtil displayDrafts(Drafts drafts);

    ResponseResultUtil mydrafts(String token);

    ResponseResultUtil deleteDrafts(Drafts drafts);
}
