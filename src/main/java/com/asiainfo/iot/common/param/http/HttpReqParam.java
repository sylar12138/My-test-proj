package com.asiainfo.iot.common.param.http;

import org.springframework.validation.annotation.Validated;

import com.asiainfo.iot.common.param.ReqParam;

import io.swagger.annotations.ApiModel;

/**
 * http 请求参数
 * @param <T>
 */
@ApiModel
@Validated
public class HttpReqParam<T> extends ReqParam<T> {

    public HttpReqParam() {
        super();
    }

    public HttpReqParam(T param) {
        super(param);
    }
}
