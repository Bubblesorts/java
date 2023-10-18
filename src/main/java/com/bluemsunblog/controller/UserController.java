package com.bluemsunblog.controller;

import com.bluemsunblog.Util.ResponseResultUtil;
import com.bluemsunblog.entity.User;
import com.bluemsunblog.service.LoginService;
import com.bluemsunblog.service.RegisterService;
import com.bluemsunblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private RegisterService registerService;

    //登录
    @PostMapping("/login")
    public ResponseResultUtil login(@RequestBody User user){
        return loginService.login(user);
    }
    //获取当前用户信息
    @GetMapping("/currentUser")
    public ResponseResultUtil currentUser(HttpServletRequest request){
        String token = request.getHeader("token");
        return userService.getUserBytoken(token);
    }
    //登出
    @GetMapping("/logout")
    public ResponseResultUtil logout(HttpServletRequest request){
        String token  = request.getHeader("token");
        return loginService.logout(token);
    }
    //注册
    @PostMapping("/register")
    public ResponseResultUtil register(@RequestBody User user){
        return registerService.regiter(user);
    }
    //上传头像
    @GetMapping("/uploadPhoto")
    public ResponseResultUtil uploadPhoto(HttpServletRequest request) throws ServletException, IOException {
        return userService.uploadPhoto(request);
    }

}
