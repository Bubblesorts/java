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
}
