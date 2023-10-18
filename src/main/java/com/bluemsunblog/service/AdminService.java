package com.bluemsunblog.service;

import com.bluemsunblog.Util.ResponseResultUtil;
import com.bluemsunblog.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminService {
    ResponseResultUtil prohibit(String token, User user);
}
