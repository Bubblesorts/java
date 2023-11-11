package com.bluemsunblog.dao;

import com.bluemsunblog.entity.Blogs;
import com.bluemsunblog.entity.Comment;
import com.bluemsunblog.entity.Drafts;
import com.bluemsunblog.entity.Examine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogsDao {
    //发布博客
    void deliver(Examine examine);
    //发布评论
    void deliverComment(Comment comment);
    //显示评论
    List<Comment> displayComment(Comment comment);
    //通过id查找博客
    Blogs getblogsByid(int blogsId);
    //我的博客
    List<Blogs> myBlogs(String blogsUsername);
    //博客分类
    List<Blogs> blogsKinds1(Blogs blogs);
    List<Blogs> blogsKinds0(Blogs blogs);
    //所有博客
    List<Blogs> getAllblogs1();
    List<Blogs> getAllblogs0();
    List<Blogs> getBlogslike1(String blogsName);
    List<Blogs> getBlogslike0(String blogsName);

    void blogsPhoto(Blogs blogs);
    void hotBlogs(Blogs blogs);

    void deleteBlogs(Blogs blogs);

    List<Blogs> hotPush();

    void blogsFile(Blogs blogs);

    void updateLike(int blogsId, int blogsLike);

    void updateCollect(int blogsId, int blogsCollect);

    List<Blogs> careBlogs(String friendMuser);

    int commentCount(int commentBlogsid);

    void insertDrafts(Drafts drafts);

    void updateDrafts(Drafts drafts);

    Drafts displayDrafts(Drafts drafts);

    void deleteDrafts(int draftsId);

    List<Drafts> mydrafts(String userName);
}
