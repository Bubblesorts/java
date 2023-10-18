package com.bluemsunblog.controller;

import com.bluemsunblog.Util.ResponseResultUtil;
import com.bluemsunblog.entity.User;
import com.bluemsunblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //封禁
    @PostMapping("/prohibit")
    public ResponseResultUtil prohibit(HttpServletRequest request,@RequestBody User user){
        String token = request.getHeader("token");
        return adminService.prohibit(token,user);
    }
}
