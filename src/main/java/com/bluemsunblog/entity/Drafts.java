package com.bluemsunblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Drafts {
    private int draftsId;
    private String draftsBlogsname;
    private String draftsUsername;
    private String draftsIntro;
    private String draftsKinds;
    private String draftsPhoto;
    private String draftsFile;
    private String draftsTime;
    private String draftsStatus; //1插入2修改
}
