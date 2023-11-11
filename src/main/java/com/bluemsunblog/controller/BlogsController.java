package com.bluemsunblog.controller;

import com.bluemsunblog.entity.Drafts;
import com.bluemsunblog.service.RandomValidateCodeService;
import com.bluemsunblog.util.FileUntil;
import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.entity.Blogs;
import com.bluemsunblog.entity.Comment;
import com.bluemsunblog.entity.Like;
import com.bluemsunblog.service.BolgsService;
import com.bluemsunblog.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/blogs")
@Slf4j
public class BlogsController {
    @Autowired
    BolgsService bolgsService;
    @Autowired
    RandomValidateCodeService randomValidateCodeService;
    //发布博客
    @PostMapping("/deliver")
    public ResponseResultUtil deliver(HttpServletRequest request,@RequestParam("file") MultipartFile headImage1,@RequestParam("photo") MultipartFile headImage2) throws ServletException, IOException {
        String token = request.getHeader("token");
        return bolgsService.deliver(token,request,headImage1,headImage2);
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
    //显示收藏
    @PostMapping("/displayCollect")
    public ResponseResultUtil displayCollect(HttpServletRequest request,@RequestBody Blogs blogs){
        String token = request.getHeader("token");
        return bolgsService.displayCollect(token,blogs);
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

    //删除博客
    @PostMapping("/deleteBlogs")
    public ResponseResultUtil deleteBlogs(HttpServletRequest request,@RequestBody Blogs blogs){
        String token = request.getHeader("token");
        return bolgsService.deleteBlogs(token,blogs);
    }
    //上传博客图片
//    @PostMapping("/blogsPhoto")
//    public ResponseResultUtil blogsPhoto(HttpServletRequest request) throws ServletException, IOException {
//        return bolgsService.blogsPhoto(request);
//    }
    //上传博客文件
//    @PostMapping("/blogsFile")
//    public ResponseResultUtil blogsFile(HttpServletRequest request,@RequestBody Blogs blogs) throws ServletException, IOException {
//        return bolgsService.blogsFile(request,blogs);
//    }

    //热门博客
    @PostMapping("/hotBlogs")
    public ResponseResultUtil hotBlogs(@RequestBody Blogs blogs){
        return bolgsService.hotBlogs(blogs);
    }

    //热门推送
    @GetMapping("/hotPush")
    public ResponseResultUtil hotPush(){
        return bolgsService.hotPush();
    }


    //关注好友的博客
    @GetMapping("/careBlogs")
    public ResponseResultUtil careBlogs(HttpServletRequest request){
        String token = request.getHeader("token");
        return bolgsService.careBlogs(token);
    }

    //博客评论数
    @PostMapping("/commentCount")
    public ResponseResultUtil commentCount(@RequestBody Blogs blogs){
        return bolgsService.commentCount(blogs);
    }

    //下载文件
    @GetMapping("/download")
    public ResponseResultUtil download(@RequestParam Integer blogsId,HttpServletResponse response) throws IOException {
        Blogs blogs=new Blogs();
        blogs.setBlogsId(blogsId);
        return bolgsService.download(blogs, response);
    }

    /**
     * 生成验证码
     */
    @GetMapping("/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response,@RequestParam Integer ran) {

            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 60);
            randomValidateCodeService.getRandcode(request, response);//输出验证码图片方法

    }
    //验证验证码
    @PostMapping("/code")
    public ResponseResultUtil code(@RequestBody Map map,HttpServletRequest request){
        System.out.println(map.get("code"));
        return bolgsService.code(map);
    }
    //保存/修改草稿箱
    @PostMapping("/drafts")
    public ResponseResultUtil drafts(HttpServletRequest request,@RequestParam("file") MultipartFile headImage1,@RequestParam("photo") MultipartFile headImage2){
        String token = request.getHeader("token");
        return bolgsService.drafts(token,request,headImage1,headImage2);
    }
    //显示草稿
    @PostMapping("/displayDrafts")
    public ResponseResultUtil displayDrafts(@RequestBody Drafts drafts){
        return bolgsService.displayDrafts(drafts);
    }
    //我的草稿
    @PostMapping("/mydrafts")
    public ResponseResultUtil mydrafts(HttpServletRequest request){
        String token = request.getHeader("token");
        return bolgsService.mydrafts(token);
    }
    //删除草稿
    @PostMapping("/deleteDrafts")
    public ResponseResultUtil deleteDrafts(@RequestBody Drafts drafts){
        return bolgsService.deleteDrafts(drafts);
    }

}
