package com.asiainfo.iot.common.param.http;

import com.asiainfo.iot.common.param.RespParam;

public class HttpRespParam<T> extends RespParam<T> {
    protected int code;


    public HttpRespParam() {
        super();
    }

    public HttpRespParam(int code) {
        this.code = code;
    }

    public HttpRespParam(int code, T data) {
        super(data);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
