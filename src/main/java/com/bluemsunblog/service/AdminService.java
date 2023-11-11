package com.bluemsunblog.service;

import com.bluemsunblog.entity.Kinds;
import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.entity.Blogs;
import com.bluemsunblog.entity.Examine;
import com.bluemsunblog.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Transactional
public interface AdminService {
    ResponseResultUtil prohibit(String token, User user);

    ResponseResultUtil checkdai(String token);

    ResponseResultUtil checkdaimsg(String token, Examine examine);

    ResponseResultUtil examineBlogs(String token, Examine examine) throws ParseException;

    ResponseResultUtil topBlogs(String token, Blogs blogs);

    ResponseResultUtil systemNotice(String token, String notice);

    ResponseResultUtil checkTop(String token);

    ResponseResultUtil kinds(Kinds kinds);
}
