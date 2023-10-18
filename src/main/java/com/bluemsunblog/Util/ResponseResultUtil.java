package com.bluemsunblog.Util;

public class ResponseResultUtil {
    private Object data;
    private Integer code;
    private String msg;

    public ResponseResultUtil() {
    }

    public ResponseResultUtil(Integer code, Object data ) {
        this.data = data;
        this.code = code;
    }

    public ResponseResultUtil(Integer code, String msg ) {
        this.msg = msg;
        this.code = code;
    }
    public ResponseResultUtil(Integer code, Object data , String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ResponseResultUtil(Result result,Object data){
        this.code=result.getCode();
        this.msg=result.getMsg();
        this.data=data;
    }
    private ResponseResultUtil(Result result){
        this.code=result.getCode();
        this.msg=result.getMsg();
    }
    public static ResponseResultUtil result(Result result,Object data){
        return new ResponseResultUtil(result,data);
    }
    public static ResponseResultUtil result(Result result){
        return new ResponseResultUtil(result);
    }

}
