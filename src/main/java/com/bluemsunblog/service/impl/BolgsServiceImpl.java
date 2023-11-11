package com.bluemsunblog.service.impl;

import com.bluemsunblog.util.FileUntil;
import com.bluemsunblog.util.ResponseResultUtil;
import com.bluemsunblog.util.Result;
import com.bluemsunblog.dao.BlogsDao;
import com.bluemsunblog.dao.MessageDao;
import com.bluemsunblog.dao.UserDao;
import com.bluemsunblog.entity.*;
import com.bluemsunblog.exception.AppException;
import com.bluemsunblog.exception.AppExceptionCodeMsg;
import com.bluemsunblog.service.BolgsService;
import com.bluemsunblog.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
@Slf4j
@Service
public class BolgsServiceImpl implements BolgsService {
    @Autowired
    LoginService loginService;
    @Autowired
    BlogsDao blogsDao;
    @Autowired
    UserDao userDao;
    @Autowired
    MessageDao messageDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public ResponseResultUtil deliver(String token, HttpServletRequest request, MultipartFile headImage1,MultipartFile headImage2) throws ServletException, IOException {
        if(request.getParameter("examineBlogsname").equals("")){
            return ResponseResultUtil.result(Result.error9);
        }
        if(request.getParameter("examineIntro").equals("")){
            return ResponseResultUtil.result(Result.error10);
        }
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        String examineFile;
        String examinePhoto;
        if(ObjectUtils.isEmpty(headImage1) || headImage1.getSize() <= 0){
            examineFile="";
        }else {
            examineFile=FileUntil.uploadFile(headImage1,request);
        }
        if(ObjectUtils.isEmpty(headImage1) || headImage1.getSize() <= 0){
            examinePhoto="";
        }else {
            examinePhoto=FileUntil.uploadImage(headImage2,request);
        }
        Examine examine=new Examine();
        examine.setExamineBlogsname(request.getParameter("examineBlogsname"));
        examine.setExamineIntro(request.getParameter("examineIntro"));
        examine.setExamineKinds(request.getParameter("examineKinds"));
        examine.setExaminePhoto(examinePhoto);
        examine.setExamineFile(examineFile);
        examine.setExamineUsername(userDao.getUserByid(user.getUserId()).getUserName());
        examine.setExamineTime(request.getParameter("examineTime"));
        blogsDao.deliver(examine);
        if (Integer.parseInt(request.getParameter("draftsId"))!=0){
            blogsDao.deleteDrafts(Integer.parseInt(request.getParameter("draftsId")));
        }
         return ResponseResultUtil.result(Result.ok4);
    }

    @Override
    public ResponseResultUtil deliverComment(String token, Comment comment) {
        if(comment.getCommentComment().equals("")){
            return ResponseResultUtil.result(Result.error11);
        }
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        comment.setCommentUsername(userDao.getUserByid(user.getUserId()).getUserName());
        blogsDao.deliverComment(comment);
        Message message = new Message();
        message.setMessageMuser(comment.getCommentReply());
        message.setMessageFuser(comment.getCommentUsername());
        message.setMessageKinds("comment");
        message.setMessageTime(comment.getCommentTime());
        message.setMessageIntro(comment.getCommentComment());
        messageDao.insert(message);
        return ResponseResultUtil.result(Result.ok5);
    }

    @Override
    public ResponseResultUtil blogsLike(String token, Like like) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        like.setLikeUsername(userDao.getUserByid(user.getUserId()).getUserName());
        String key = "like"+String.valueOf(like.getBlogsId())+like.getLikeUsername();
        Object o = redisTemplate.opsForValue().get(key);
        if(o==null){
            redisTemplate.opsForValue().set(key,like.getLike());
            userDao.updateLike(blogsDao.getblogsByid(like.getBlogsId()).getBlogsUsername(),userDao.getUserByusername(blogsDao.getblogsByid(like.getBlogsId()).getBlogsUsername()).getUserMylike()+1);
            blogsDao.updateLike(like.getBlogsId(),blogsDao.getblogsByid(like.getBlogsId()).getBlogsLike()+1);
            return ResponseResultUtil.result(Result.ok6);
        }else {
            redisTemplate.delete(key);
            userDao.updateLike(blogsDao.getblogsByid(like.getBlogsId()).getBlogsUsername(),userDao.getUserByusername(blogsDao.getblogsByid(like.getBlogsId()).getBlogsUsername()).getUserMylike()-1);
            blogsDao.updateLike(like.getBlogsId(),blogsDao.getblogsByid(like.getBlogsId()).getBlogsLike()-1);
            return ResponseResultUtil.result(Result.ok8);
        }

    }

    @Override
    public ResponseResultUtil displayLike(String token, Blogs blogs) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        String key = "like"+String.valueOf(blogs.getBlogsId())+user.getUserName();
        Object o = redisTemplate.opsForValue().get(key);
        return ResponseResultUtil.result(Result.ok7,o);
    }

    @Override
    public ResponseResultUtil diaplayComment(String token, Comment comment) {
        User user = loginService.checkToken(token);
//        if(user==null){
//            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
//        }
//        comment.setCommentUsername(userDao.getUserByid(user.getUserId()).getUserName());
        List<Comment> comments = blogsDao.displayComment(comment);
        return ResponseResultUtil.result(Result.ok9,comments);
    }

    @Override
    public ResponseResultUtil commentLike(String token, Comment comment) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        comment.setCommentUsername(userDao.getUserByid(user.getUserId()).getUserName());
        String key = "comment"+String.valueOf(comment.getCommentBlogsid())+comment.getCommentUsername()+comment.getCommentMasteruser();
        if(redisTemplate.opsForHash().get(key,comment.getCommentId())==null)
        {
            redisTemplate.opsForHash().put(key,comment.getCommentId(),comment.getCommentId());
            return ResponseResultUtil.result(Result.ok10);
        }else {
            redisTemplate.opsForHash().delete(key,comment.getCommentId());
            return ResponseResultUtil.result(Result.ok11);
        }
    }

    @Override
    public ResponseResultUtil diaplayCommentlike(String token, Comment comment) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        comment.setCommentUsername(userDao.getUserByid(user.getUserId()).getUserName());
        String key = "comment"+String.valueOf(comment.getCommentBlogsid())+comment.getCommentUsername()+comment.getCommentMasteruser();
        return ResponseResultUtil.result(Result.ok12,redisTemplate.opsForHash().keys(key));
    }

    @Override
    public ResponseResultUtil collectBlogs(String token, Blogs blogs) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        String key = "collect"+userDao.getUserByid(user.getUserId()).getUserName();
        if(redisTemplate.opsForHash().get(key,blogs.getBlogsId())==null){
            redisTemplate.opsForHash().put(key,blogs.getBlogsId(),blogs.getBlogsId());
            user.setUserCollect(redisTemplate.opsForHash().keys(key).size());
            userDao.updateCollect(user);
            userDao.updateMycollect(blogsDao.getblogsByid(blogs.getBlogsId()).getBlogsUsername(),userDao.getUserByusername(blogsDao.getblogsByid(blogs.getBlogsId()).getBlogsUsername()).getUserMycollect()+1);
            blogsDao.updateCollect(blogs.getBlogsId(),blogsDao.getblogsByid(blogs.getBlogsId()).getBlogsCollect()+1);
            return ResponseResultUtil.result(Result.ok13);
        }else {
            redisTemplate.opsForHash().delete(key,blogs.getBlogsId());
            user.setUserCollect(redisTemplate.opsForHash().keys(key).size());
            userDao.updateCollect(user);
            userDao.updateMycollect(blogsDao.getblogsByid(blogs.getBlogsId()).getBlogsUsername(),userDao.getUserByusername(blogsDao.getblogsByid(blogs.getBlogsId()).getBlogsUsername()).getUserMycollect()-1);
            blogsDao.updateCollect(blogs.getBlogsId(),blogsDao.getblogsByid(blogs.getBlogsId()).getBlogsCollect()-1);
            return ResponseResultUtil.result(Result.ok14);
        }

    }

    @Override
    public ResponseResultUtil myCollect(String token) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }else {
            String key = "collect"+userDao.getUserByid(user.getUserId()).getUserName();
            return ResponseResultUtil.result(Result.ok15,redisTemplate.opsForHash().keys(key));
        }

    }

    @Override
    public ResponseResultUtil blogsMessage(Blogs blogs) {
        return ResponseResultUtil.result(Result.ok16,blogsDao.getblogsByid(blogs.getBlogsId()));
    }

    @Override
    public ResponseResultUtil myBlogs(String token) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        List<Blogs> list = blogsDao.myBlogs(userDao.getUserByid(user.getUserId()).getUserName());
        return ResponseResultUtil.result(Result.ok18,list);
    }

    @Override
    public ResponseResultUtil blogsKinds(Blogs blogs) {
        if(blogs.getBlogsKinds().equals("")){
            blogs.setBlogsKinds("其他");
        }
        List<Blogs> list = blogsDao.blogsKinds1(blogs);
        list.addAll(blogsDao.blogsKinds0(blogs));
        return ResponseResultUtil.result(Result.ok19,list);
    }

    @Override
    public ResponseResultUtil searchBlogs(Blogs blogs) {
        if(blogs.getBlogsName().equals("")){

            List<Blogs> list = blogsDao.getAllblogs1();
            list.addAll(blogsDao.getAllblogs0());
            return ResponseResultUtil.result(Result.ok20,list);
        }else {
            List<Blogs> list = blogsDao.getBlogslike1(blogs.getBlogsName());
            list.addAll(blogsDao.getBlogslike0(blogs.getBlogsName()));
            return ResponseResultUtil.result(Result.ok20,list);
        }
    }

    @Override
    public ResponseResultUtil deleteBlogs(String token, Blogs blogs) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        blogsDao.deleteBlogs(blogs);
        userDao.updateMyblogs(blogsDao.myBlogs(user.getUserName()).size(), user.getUserName() );
        return ResponseResultUtil.result(Result.ok20);
    }

//    @Override
//    public ResponseResultUtil blogsPhoto(HttpServletRequest request) throws ServletException, IOException {
//        String token  = request.getHeader("token");
//        User user = loginService.checkToken(token);
//        if(user==null){
//            return ResponseResultUtil.result(Result.error8);
//        }
//        String blogsPhoto = FileUntil.address(request);
//        Blogs blogs=new Blogs();
//        blogs.setBlogsId(Integer.parseInt(request.getParameter("blogsId")));
//        System.out.println(blogs.getBlogsId());
//        blogs.setBlogsPhoto(blogsPhoto);
//        blogsDao.blogsPhoto(blogs);
//        return ResponseResultUtil.result(Result.ok17);
//    }
//    @Override
//    public ResponseResultUtil blogsFile(HttpServletRequest request, Blogs blogs) throws ServletException, IOException {
//        String token  = request.getHeader("token");
//        User user = loginService.checkToken(token);
//        if(user==null){
//            return ResponseResultUtil.result(Result.error8);
//        }
//        String blogsFile = FileUntil.address(request);
//        blogs.setBlogsFile(blogsFile);
//        blogsDao.blogsFile(blogs);
//        return ResponseResultUtil.result(Result.ok20);
//    }

    @Override
    public ResponseResultUtil displayCollect(String token, Blogs blogs) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        String key = "collect"+userDao.getUserByid(user.getUserId()).getUserName();
        return ResponseResultUtil.result(Result.ok20,redisTemplate.opsForHash().get(key,blogs.getBlogsId()));
    }

    @Override
    public ResponseResultUtil careBlogs(String token) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        List<Blogs> list=blogsDao.careBlogs(user.getUserName());
        return ResponseResultUtil.result(Result.ok20,list);
    }

    @Override
    public ResponseResultUtil commentCount(Blogs blogs) {
        int count = blogsDao.commentCount(blogs.getBlogsId());
        return ResponseResultUtil.result(Result.ok20,count);
    }

    @Override
    public ResponseResultUtil download(Blogs blogs, HttpServletResponse response) throws IOException {
        blogs.setBlogsFile(blogsDao.getblogsByid(blogs.getBlogsId()).getBlogsFile());
        String FullPath = blogs.getBlogsFile();//将文件的统一储存路径和文件名拼接得到文件全路径
        File packetFile = new File(FullPath);
        String dir = "/root/file/";
        String fileName = FullPath.substring(FullPath.lastIndexOf("/")+1); //下载的文件名
        System.out.println(fileName);
        File file = new File(dir+fileName);
        // 如果文件名存在，则进行下载
        if (file.exists()) {
            // 配置文件下载
//            response.reset();
            // 下载文件能正常显示中文
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//            response.setContentType("application/octet-stream");
            response.setHeader("Content-Type", "application/octet-stream");// 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
//            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();

                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
//                response.getWriter();
                System.out.println("Download the song successfully!");
//            } catch (Exception e) {
//                System.out.println("Download the song failed!");
//            } finally {
//                if (bis != null) {
//                    try {
//                        bis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (fis != null) {
//                    try {
//                        fis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
        } else {//对应文件不存在
            try {
                //设置响应的数据类型是html文本，并且告知浏览器，使用UTF-8 来编码。
                response.setContentType("text/html;charset=UTF-8");

                //String这个类里面， getBytes()方法使用的码表，是UTF-8，  跟tomcat的默认码表没关系。 tomcat iso-8859-1
                String csn = Charset.defaultCharset().name();

                System.out.println("默认的String里面的getBytes方法使用的码表是： " + csn);

                //1. 指定浏览器看这份数据使用的码表
                response.setHeader("Content-Type", "text/html;charset=UTF-8");
                OutputStream os = response.getOutputStream();

                os.write("对应文件不存在".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
//                return R.error("-1","对应文件不存在");
        }
        return ResponseResultUtil.result(Result.ok20,"http://39.101.77.55:8081/blogs/download?blogsId="+blogs.getBlogsId());
    }

    @Override
    public ResponseResultUtil code(Map map) {
        System.out.println(redisTemplate.opsForValue().get("RANDOMCODEKEY"));
        if(map.get("code").equals(redisTemplate.opsForValue().get("RANDOMCODEKEY"))){
            return ResponseResultUtil.result(Result.ok20);
        }else {
            return ResponseResultUtil.result(Result.error18);
        }
    }

    @Override
    public ResponseResultUtil drafts( String token,HttpServletRequest request,MultipartFile headImage1,MultipartFile headImage2) {
        User user = loginService.checkToken(token);
//        if(user==null){
//            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
//        }
        Drafts drafts=new Drafts();
        if(request.getParameter("draftsBlogsname").equals("")){
            return ResponseResultUtil.result(Result.error9);
        }else {
            drafts.setDraftsBlogsname(request.getParameter("draftsBlogsname"));
        }
        if (request.getParameter("draftsIntro").equals("")){
            drafts.setDraftsIntro("");
        }else {
            drafts.setDraftsIntro(request.getParameter("draftsIntro"));
        }
        if (request.getParameter("draftsKinds").equals("")){
            drafts.setDraftsKinds("");
        }else {
            drafts.setDraftsKinds(request.getParameter("draftsKinds"));
        }
        drafts.setDraftsUsername(user.getUserName());
        drafts.setDraftsFile(FileUntil.uploadFile(headImage1,request));
        drafts.setDraftsPhoto(FileUntil.uploadImage(headImage2,request));
        if ( request.getParameter("draftsStatus").equals("2")){
            drafts.setDraftsId(Integer.parseInt(request.getParameter("draftsId")));
            blogsDao.updateDrafts(drafts);
            return ResponseResultUtil.result(Result.ok20);
        }else {
           blogsDao.insertDrafts(drafts);
            return ResponseResultUtil.result(Result.ok20);
        }
    }

    @Override
    public ResponseResultUtil displayDrafts(Drafts drafts) {
        Drafts drafts1=blogsDao.displayDrafts(drafts);
        return ResponseResultUtil.result(Result.ok20,drafts1);
    }

    @Override
    public ResponseResultUtil mydrafts(String token) {
        User user = loginService.checkToken(token);
        if(user==null){
            throw new AppException(AppExceptionCodeMsg.USER_PLEASE_LOGIN);
        }
        List<Drafts> list=blogsDao.mydrafts(user.getUserName());
        return ResponseResultUtil.result(Result.ok20,list);
    }

    @Override
    public ResponseResultUtil deleteDrafts(Drafts drafts) {
        blogsDao.deleteDrafts(drafts.getDraftsId());
        return ResponseResultUtil.result(Result.ok20);
    }


    @Override
    public ResponseResultUtil hotBlogs(Blogs blogs) {
        blogs.setBlogsClick(blogsDao.getblogsByid(blogs.getBlogsId()).getBlogsClick()+1);
        blogsDao.hotBlogs(blogs);
        return ResponseResultUtil.result(Result.ok20);
    }

    @Override
    public ResponseResultUtil hotPush() {
        List<Blogs> list = blogsDao.hotPush();
        return ResponseResultUtil.result(Result.ok20,list);
    }


}
