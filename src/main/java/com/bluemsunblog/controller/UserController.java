package com.bluemsunblog.controller;

import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.entity.User;
import com.bluemsunblog.service.LoginService;
import com.bluemsunblog.service.RegisterService;
import com.bluemsunblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
    public ResponseResultUtil currentUser(HttpServletResponse response,HttpServletRequest request){
//        response.setHeader("Access-Control-Allow-Origin","*");
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
    @PostMapping("/uploadPhoto")
    public ResponseResultUtil uploadPhoto(HttpServletRequest request,@RequestParam("file") MultipartFile headImage) throws ServletException, IOException {
        return userService.uploadPhoto(request,headImage);
    }
    //修改个人信息
    @PostMapping("/updateUser")
    public ResponseResultUtil updateUser(HttpServletRequest request,@RequestBody User user){
        String token  = request.getHeader("token");
        return userService.updateUser(token,user);
    }
    //搜索作者
    @PostMapping("/searchUser")
    public ResponseResultUtil searchUser(HttpServletRequest request,@RequestBody User user){
        String token  = request.getHeader("token");
        return userService.searchUser(token,user);
    }
//    我的好友
    @GetMapping("/myfriend")
    public ResponseResultUtil myfriend(HttpServletRequest request,@RequestParam Integer status){
        String token  = request.getHeader("token");
        return userService.myfriend(token,status);
    }

    //显示关注
    @PostMapping("/displayCare")
    public ResponseResultUtil displayCare(HttpServletRequest request,@RequestBody User user){
        String token  = request.getHeader("token");
        return userService.displayCare(token,user);
    }

    //忘记密码
    @PostMapping("/email")
    public ResponseResultUtil email(@RequestBody User user) throws MessagingException {
        return userService.email(user);
    }
    //重置密码
    @PostMapping("/updatePwd")
    public ResponseResultUtil updatePwd(@RequestBody User user){
        return userService.updatePwd(user);
    }

    //好友信息
    @PostMapping("/friendMessage")
    public ResponseResultUtil friendMessage(@RequestBody User user){
        return userService.friendMessage(user);
    }
}
