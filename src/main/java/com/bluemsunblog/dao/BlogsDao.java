package com.bluemsunblog.dao;

import com.bluemsunblog.entity.Blogs;
import com.bluemsunblog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogsDao {
    //发布博客
    void deliver(Blogs blogs);
    //发布评论
    void deliverComment(Comment comment);
    //显示评论
    List<Comment> displayComment(Comment comment);
    //通过id查找博客
    Blogs getblogsByid(int blogsId);
    //我的博客
    List<Blogs> myBlogs(String blogsUsername);
    //博客分类
    List<Blogs> blogsKinds(Blogs blogs);
    //所有博客
    List<Blogs> getAllblogs();
    List<Blogs> getBlogslike(String blogsName);
}
