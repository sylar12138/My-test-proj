package com.asiainfo.iot.common.param.rpc;

import com.asiainfo.iot.common.param.page.Page;

import java.util.Map;

public class RpcRespPageParam<T> extends RpcRespParam<T> {
    protected Page page;

    public RpcRespPageParam() {
        super();
    }

    public RpcRespPageParam(T data, Page page) {
        super(data);
        this.page = page;
    }

    public RpcRespPageParam(T data, Map ext, Page page) {
        super(data, ext);
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
