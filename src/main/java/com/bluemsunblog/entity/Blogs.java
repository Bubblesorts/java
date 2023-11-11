package com.bluemsunblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Blogs {
    private int blogsId;
    private String blogsName;
    private String blogsIntro;
    private String blogsUsername;
    private String blogsKinds;
    private String blogsPhoto;
    private int blogsTop; //0未置顶，1置顶
    private int blogsClick;
    private String blogsFile;
    private int blogsLike;
    private int blogsCollect;
    private String userPhoto;
    private String fileDown;
    private String blogsTime;

}
