package com.asiainfo.iot.common.param.rpc;

import com.asiainfo.iot.common.param.ReqParam;

import java.util.Map;

public class RpcReqParam<T> extends ReqParam<T> {

    protected Map ext;


    public RpcReqParam() {
        super();
    }

    public RpcReqParam(T param) {
        super(param);
    }

    public RpcReqParam(T param, Map ext) {
        super(param);
        this.ext = ext;
    }


    public Map getExt() {
        return ext;
    }

    public void setExt(Map ext) {
        this.ext = ext;
    }
}
