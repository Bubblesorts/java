package com.bluemsunblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Examine {
    private int examineId;
    private String examineBlogsname;
    private String examineUsername;
    private String examineIntro;
    private String examineKinds;
    private int examineStatus;//0未审核 1审核通过 2审核失败
    private String examinePhoto;
    private String examineFile;
    private int draftsId;
    private String examineTime;
}
