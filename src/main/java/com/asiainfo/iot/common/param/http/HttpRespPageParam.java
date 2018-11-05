package com.asiainfo.iot.common.param.http;

import com.asiainfo.iot.common.param.page.Page;

public class HttpRespPageParam<T> extends HttpRespParam<T> {
    protected Page page;

    public HttpRespPageParam() {
        super();
    }

    public HttpRespPageParam(int code, T data) {
        super(code, data);
    }

    public HttpRespPageParam(int code, T data, Page page) {
        super(code, data);
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
