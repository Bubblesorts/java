package com.bluemsunblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private int messageId;
    private String messageMuser;//谁回复他
    private String messageFuser;//谁
    private String messageKinds;
    private String messageTime;
    private String messageIntro;
    private String userPhoto;
}
