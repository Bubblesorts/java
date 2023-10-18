package com.bluemsunblog.Util;

public enum Result {
    error1(501,"用户名或密码不能为空"),
    error2(502,"密码错误"),
    error3(503,"用户名不能为空"),
    error4(504,"密码不能为空"),
    error5(505,"邮箱不能为空"),
    error6(506,"电话号码不能为空"),
    error7(507,"用户名已存在，请重新输入"),
    error8(508,"未登录请重新登录"),
    error9(509,"博客名不为空"),
    error10(510,"博客内容不为空"),
    error11(511,"评论不为空"),
    error12(512,"权限不足"),
    error13(512,"用户已封禁"),
    ok(200,"登陆成功"),
    ok1(201,"退出成功"),
    ok2(202,"注册成功"),
    ok3(203,"获得用户信息成功"),
    ok4(204,"发布博客成功"),
    ok5(205,"发布评论成功"),
    ok6(206,"点赞成功"),
    ok7(207,"显示点赞"),
    ok8(208,"取消点赞"),
    ok9(209,"显示评论"),
    ok10(210,"点赞评论"),
    ok11(211,"取消点赞评论"),
    ok12(212,"显示点赞评论"),
    ok13(213,"收藏博客"),
    ok14(214,"取消收藏"),
    ok15(215,"显示收藏"),
    ok16(216,"显示博客信息"),
    ok17(217,"上传头像成功"),
    ok18(218,"我的博客"),
    ok19(219,"博客分类"),
    ok20(220,"成功"),
    ok21(221,"封禁成功"),
    ok22(222,"取消封禁"),


    ;

    private int code;
    private String msg;
    Result(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public int getCode(){
        return this.code;
    }
    public String getMsg(){
        return this.msg;
    }
}
