package com.timecash.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author:tanghongwei@jumaps.com
 * @Date:2019-12-05
 * @Copyright: 2019 juma.com All Rights Reserved
 */
@Data
public class Msg implements Serializable {

    private static final  String  OK_CODE="OK";
    private static final  String  OK_MSG="操作成功";
    private static final  String  LOGIN_CODE="LOGIN";
    private static final  String  ERR_CODE="err";
    private static final  String  ERR_MSG="操作失败";
    private String code;
    private String descp;
    private Object data;

    private Msg(String code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    public  static <T> Msg ok(T data){
        Msg msgTmp = new Msg(OK_CODE,OK_MSG);
        msgTmp.data = data;
        return msgTmp;
    }
    public  static  Msg ok(){
        return ok(null);
    }
    public  static  Msg err(String msg){
        Msg msgTmp = new Msg(ERR_CODE,msg);
        return msgTmp;
    }
    public  static Msg  err(){
        return err(ERR_MSG);
    }
    public  static Msg  login(){
        Msg msg = new Msg(LOGIN_CODE , "请登录");
        return msg;
    }

}
