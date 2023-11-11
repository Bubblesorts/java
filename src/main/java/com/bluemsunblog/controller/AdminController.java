package com.bluemsunblog.controller;

import com.bluemsunblog.entity.Kinds;
import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.entity.Blogs;
import com.bluemsunblog.entity.Examine;
import com.bluemsunblog.entity.User;
import com.bluemsunblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //封禁
    @PostMapping("/prohibit")
    public ResponseResultUtil prohibit(HttpServletRequest request, HttpServletResponse response, @RequestBody User user){

        String token = request.getHeader("token");
        return adminService.prohibit(token,user);
    }

    //查看待审核博客
    @GetMapping("/checkdai")
    public ResponseResultUtil checkdai(HttpServletRequest request){
        String token = request.getHeader("token");
        return adminService.checkdai(token);
    }

    //查看待审核博客信息
    @PostMapping("checkdaimsg")
    public ResponseResultUtil checkdaimsg(HttpServletRequest request, @RequestBody Examine examine){
        String token = request.getHeader("token");
        return adminService.checkdaimsg(token,examine);
    }

    //审核博客
    @PostMapping("/examineBlogs")
    public ResponseResultUtil examineBlogs(HttpServletRequest request,@RequestBody Examine examine) throws ParseException {
        String token = request.getHeader("token");
        return adminService.examineBlogs(token,examine);
    }
    //设置置顶博客
    @PostMapping("/topBlogs")
    public ResponseResultUtil topBlogs(HttpServletRequest request, @RequestBody Blogs blogs){
        String token = request.getHeader("token");
        return adminService.topBlogs(token,blogs);
    }
    //发送系统通知
    @PostMapping("/systemNotice")
    public ResponseResultUtil systemNotice(HttpServletRequest request, @RequestBody Map map){
        String token = request.getHeader("token");
        return adminService.systemNotice(token,map.get("notice").toString());
    }
    //查看所有置顶博客
    @GetMapping("/checkTop")
    public ResponseResultUtil checkTop(HttpServletRequest request){
        String token = request.getHeader("token");
        return adminService.checkTop(token);
    }
    //博客种类
    @PostMapping("/kinds")
    public ResponseResultUtil kinds(@RequestBody Kinds kinds){
        return adminService.kinds(kinds);
    }

}
