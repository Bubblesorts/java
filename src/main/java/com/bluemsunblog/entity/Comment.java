package com.bluemsunblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
    private int commentId;
    private String commentUsername;
    private String commentComment;
    private int commentBlogsid;
    private String commentTime;
    private String commentMasteruser;
    private String commentReply;
    private String userPhoto;
}
