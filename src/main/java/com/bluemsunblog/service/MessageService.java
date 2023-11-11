package com.bluemsunblog.service;

import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.entity.Friend;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MessageService {
    ResponseResultUtil message(String token);

    ResponseResultUtil careFriend(String token, Friend friend);
}
