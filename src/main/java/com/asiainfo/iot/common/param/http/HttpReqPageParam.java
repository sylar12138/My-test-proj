package com.asiainfo.iot.common.param.http;

import com.asiainfo.iot.common.param.page.Page;

import io.swagger.annotations.ApiModelProperty;

/**
 * http 请求 带分页 参数
 * @param <T>
 */
public class HttpReqPageParam<T> extends HttpReqParam<T> {
    
	@ApiModelProperty
	protected Page page;
}
