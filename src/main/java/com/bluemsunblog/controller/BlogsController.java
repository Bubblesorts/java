package com.bluemsunblog.controller;

import com.bluemsunblog.Util.ResponseResultUtil;
import com.bluemsunblog.entity.Blogs;
import com.bluemsunblog.entity.Comment;
import com.bluemsunblog.entity.Like;
import com.bluemsunblog.service.BolgsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/blogs")
public class BlogsController {
    @Autowired
    BolgsService bolgsService;
    //发布博客
    @PostMapping("/deliver")
    public ResponseResultUtil deliver(HttpServletRequest request, @RequestBody Blogs blogs){
        String token = request.getHeader("token");
        return bolgsService.deliver(token,blogs);
    }

    //发布评论
    @PostMapping("/deliverComment")
    public ResponseResultUtil deliverComment(HttpServletRequest request, @RequestBody Comment comment){
        String token = request.getHeader("token");
        return bolgsService.deliverComment(token,comment);
    }

    //显示评论
    @PostMapping("/displayComment")
    public ResponseResultUtil displayComment(HttpServletRequest request,@RequestBody Comment comment){
        String token = request.getHeader("token");
        return bolgsService.diaplayComment(token,comment);
    }

    //博客点赞
    @PostMapping("/blogsLike")
    public ResponseResultUtil blogsLike(HttpServletRequest request, @RequestBody Like like){
        String token = request.getHeader("token");
        return bolgsService.blogsLike(token,like);
    }

    //显示点赞
    @PostMapping("/displayLike")
    public ResponseResultUtil displayLike(HttpServletRequest request, @RequestBody Blogs blogs){
        String token = request.getHeader("token");
        return bolgsService.displayLike(token,blogs);
    }
    //点赞评论
    @PostMapping("/commentLike")
    public ResponseResultUtil commentLike(HttpServletRequest request,@RequestBody Comment comment)
    {
        String token = request.getHeader("token");
        return bolgsService.commentLike(token,comment);
    }

    //显示评论点赞
    @PostMapping("/displayCommentlike")
    public ResponseResultUtil displayCommentlike(HttpServletRequest request,@RequestBody Comment comment)
    {
        String token = request.getHeader("token");
        return bolgsService.diaplayCommentlike(token,comment);
    }

    //收藏博客
    @PostMapping("/collectBlogs")
    public ResponseResultUtil collectBlogs(HttpServletRequest request,@RequestBody Blogs blogs){
        String token = request.getHeader("token");
        return bolgsService.collectBlogs(token,blogs);
    }
    //我的收藏
    @GetMapping("/myCollect")
    public ResponseResultUtil myCollect(HttpServletRequest request){
        String token = request.getHeader("token");
        return bolgsService.myCollect(token);
    }
    //通过id查询博客信息
    @PostMapping("/blogsMessage")
    public ResponseResultUtil blogsMessage(@RequestBody Blogs blogs){
        return bolgsService.blogsMessage(blogs);
    }

    //我的博客
    @GetMapping("/myBlogs")
    public ResponseResultUtil myBlogs(HttpServletRequest request){
        String token = request.getHeader("token");
        return bolgsService.myBlogs(token);
    }

    //博客分类
    @PostMapping("/blogsKinds")
    public ResponseResultUtil blogsKinds(@RequestBody Blogs blogs){
        return bolgsService.blogsKinds(blogs);
    }

    //搜索博客
    @PostMapping("/searchBlogs")
    public ResponseResultUtil searchBlogs(@RequestBody Blogs blogs){
        return bolgsService.searchBlogs(blogs);
    }

}
