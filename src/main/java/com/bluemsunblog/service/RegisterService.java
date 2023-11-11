package com.bluemsunblog.service;

import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RegisterService {
    //注册
    ResponseResultUtil regiter(User user);
}
