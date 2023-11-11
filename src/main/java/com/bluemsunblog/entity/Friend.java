package com.bluemsunblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Friend {
    private int friendId;
    private String friendMuser;//我
    private String friendFuser;//我的好友
    private String friendTime;
    private String userPhoto;
}
