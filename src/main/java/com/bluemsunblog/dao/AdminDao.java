package com.bluemsunblog.dao;

import com.bluemsunblog.entity.Blogs;
import com.bluemsunblog.entity.Examine;
import com.bluemsunblog.entity.Kinds;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminDao {

    void prohibit(int userProhibit,String userName);

    List<Examine> checkdai();

    Examine checkdaimsg(Examine examine);

    void examineBlogs(Examine examine);

    void examinesuccess(Blogs blogs);

    void topBlogs(Blogs blogs);
    void topBlogs0(Blogs blogs);

    List<Blogs> checkTop();

    List<Kinds> allKinds();

    void addKinds(Kinds kinds);
}
