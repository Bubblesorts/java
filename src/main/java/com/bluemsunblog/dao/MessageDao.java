package com.bluemsunblog.dao;

import com.bluemsunblog.entity.Friend;
import com.bluemsunblog.entity.Message;
import com.bluemsunblog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface MessageDao {
    void insert(Message message);

    List<Message> message(String messageMuser);

    void careFriend(Friend friend);

    void deleteCare(Friend friend);
}
