package com.asiainfo.iot.common.param.rpc;

import com.asiainfo.iot.common.param.RespParam;

import java.util.Map;

public class RpcRespParam<T> extends RespParam<T> {
    protected Map ext;


    public RpcRespParam() {
        super();
    }

    public RpcRespParam(T data) {
        super(data);
    }

    public RpcRespParam(T data, Map ext) {
        super(data);
        this.ext = ext;
    }

    public Map getExt() {
        return ext;
    }

    public void setExt(Map ext) {
        this.ext = ext;
    }
}
