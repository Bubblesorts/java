package com.bluemsunblog.controller;

import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.entity.Friend;
import com.bluemsunblog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;
    //消息系统
    @GetMapping("/message")
    public ResponseResultUtil message(HttpServletRequest request){
        String token = request.getHeader("token");
        return messageService.message(token);
    }
    //关注好友
    @PostMapping("/careFriend")
    public ResponseResultUtil careFriend(HttpServletRequest request, @RequestBody Friend friend){
        String token = request.getHeader("token");
        return messageService.careFriend(token,friend);
    }


}
