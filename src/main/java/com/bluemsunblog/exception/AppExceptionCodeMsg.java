package com.bluemsunblog.exception;

//这个枚举类中定义的都是跟业务有关的异常
public enum AppExceptionCodeMsg {
    USER_NOT_EXISTS(502,"用户不存在"),
    USER_ALREADY_EXISTS(508,"用户名已存在，请重新输入"),
    USER_PLEASE_LOGIN(509,"请登录");

    private int code ;
    private String msg ;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    AppExceptionCodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}