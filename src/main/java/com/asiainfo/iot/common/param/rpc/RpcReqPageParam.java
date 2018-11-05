package com.asiainfo.iot.common.param.rpc;

import com.asiainfo.iot.common.param.page.Page;

import java.util.Map;

public class RpcReqPageParam<T> extends RpcReqParam<T> {
    protected Page page;


    public RpcReqPageParam() {
        super();
    }

    public RpcReqPageParam(T param, Page page) {
        super(param);
        this.page = page;
    }

    public RpcReqPageParam(T param, Map ext, Page page) {
        super(param, ext);
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
