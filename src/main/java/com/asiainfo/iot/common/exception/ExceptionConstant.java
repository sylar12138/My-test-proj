package com.asiainfo.iot.common.exception;

/**
 * 全局统一返回码
 */
public class ExceptionConstant {

    //失败
    public static final int ERROR = 0;

    //成功
    public static final int SUCCESS = 1;

    //token过期
    public static final int TOKEN_EXPIRE = 1001;

    //签名错误
    public static final int SIGN_ERR = 1002;

    //时间戳过期
    public static final int TIMESTAMP_EXPIRE = 1003;

    //权限不够，没有足够权限
    public static final int AUTH_ERR = 1004;

    //参数不能为空
    public static final int NOT_NULL = 1101;

    //参数类型错误
    public static final int CLASS_CAST_EXCEPTION = 1102;




}
